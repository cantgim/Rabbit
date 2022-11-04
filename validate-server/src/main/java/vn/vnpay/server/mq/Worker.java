package vn.vnpay.server.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import vn.vnpay.common.constant.Constant;
import vn.vnpay.common.domain.Response;
import vn.vnpay.common.domain.Transaction;
import vn.vnpay.server.redis.JedisUtils;

import static vn.vnpay.common.constant.Constant.RabbitMQ.*;

@Slf4j
public class Worker {

    private final Connection connection;
    private final Channel channel;

    public Worker(String hostName, String queueName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        final boolean autoAck = false;
        factory.setHost(hostName);
        connection = factory.newConnection();
        log.info("Connection created.");

        channel = connection.createChannel();
        channel.queueDeclare(queueName, DURABLE, EXCLUSIVE, AUTO_DELETE, null);
        channel.queuePurge(queueName);
        channel.basicQos(PREFETCH_COUNT);
        log.info("Channel created.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info("New transaction received.");
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();
            Transaction transaction = SerializationUtils.deserialize(delivery.getBody());
            channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps,
                    SerializationUtils.serialize(createTransaction(transaction)));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queueName, autoAck, deliverCallback, cTag -> {
        });
        log.info("Worker listening...");
    }

    /**
     * TODO(cantgim): Refactor to common function
     *
     * @param transaction
     * @return
     */
    private Response createTransaction(Transaction transaction) {
        if (transaction == null) {
            return Response.INVALID_TRANSACTION;
        }
        log.info("Transaction verifying {}", transaction);

        JedisUtils jedisUtils = new JedisUtils();
        log.info("Verifying transactionId and trace...");
        if (jedisUtils.exist(String.valueOf(transaction.getTransactionId()),
                Constant.TransactionKey.trace).isPresent()) {
            log.info("Invalid transactionId/trace");
            return Response.IDENTITY_FAILURE;
        }

        log.info("Verifying payDate...");
        long timeMillis = System.currentTimeMillis() - transaction.getPayDate().getTime();
        if (timeMillis <= 0 || (timeMillis / 60000 > Constant.Transaction.TIMEOUT_MIN)) {
            log.info("Request payment timeout");
            return Response.TIMEOUT_FAILURE;
        }

        log.info("Verifying amount...");
        if (transaction.getAmount() <= 0) {
            log.info("Transaction amount invalid");
            return Response.AMOUNT_FAILURE;
        }
        jedisUtils.save(String.valueOf(transaction.getTransactionId()), transaction);

        return Response.SUCCESS;
    }
}
