package vn.vnpay.client.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import vn.vnpay.server.constant.Constant;
import vn.vnpay.server.domain.Transaction;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * TODO(cantgim): implements channel pool
 */
@Slf4j
public class RabbitMQConfiguration implements AutoCloseable{
    private Connection connection;
    private Channel channel;

    public RabbitMQConfiguration(String hostName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        connection = factory.newConnection();
        log.info("[x] Connection created.");
        channel = connection.createChannel();
        log.info("[x] Channel created.");
    }

    public boolean call(Transaction transaction) throws IOException, ExecutionException,
            InterruptedException {
     final String corId = UUID.randomUUID().toString();
     final boolean autoAck = true;
     String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props =
                new AMQP.BasicProperties().builder().correlationId(corId).replyTo(replyQueueName).build();

        channel.basicPublish("", Constant.RabbitMQ.REQUEST_QUEUE_NAME, props, SerializationUtils.serialize(transaction));
        log.info("[x] Transaction sending...");

        final CompletableFuture<Boolean> completer = new CompletableFuture<>();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info("[x] Response received.");
            if (delivery.getProperties().getCorrelationId().equals(corId)) {
                completer.complete(SerializationUtils.deserialize(delivery.getBody()));
            }
        };
        String cTag = channel.basicConsume(replyQueueName, autoAck, deliverCallback,
                consumerTag -> {});
        boolean result = completer.get();
        channel.basicCancel(cTag);
        return result;
    }

    @Override
    public void close() throws IOException {
        connection.close();
        log.info("[x] Client close.");
    }
}
