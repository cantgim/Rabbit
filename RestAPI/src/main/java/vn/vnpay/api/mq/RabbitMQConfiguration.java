package vn.vnpay.api.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import vn.vnpay.server.constant.Constant;
import vn.vnpay.server.domain.Response;
import vn.vnpay.server.domain.Transaction;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * TODO(cantgim): implements channel object pool
 */
@Slf4j
public class RabbitMQConfiguration implements AutoCloseable {
    private final Connection connection;
    private final Channel channel;

    public RabbitMQConfiguration(String hostName) throws IOException, TimeoutException {
        log.info("Connection creating...");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        connection = factory.newConnection();
        log.info("Connection created.");
        channel = connection.createChannel();
        log.info("Channel created.");
    }

    public Response call(Transaction transaction) throws IOException, ExecutionException,
            InterruptedException {
        log.info("Begin call.");
        if (transaction == null) {
            log.info("Transaction must not be null.");
            return new Response(Constant.Response.FAILURE_CODE, Constant.Response.FAILURE_MESSAGE);
        }
        final String corId = UUID.randomUUID().toString();
        final boolean autoAck = true;
        String replyQueueName = channel.queueDeclare().getQueue();
        log.info("Reply queue setup.");
        AMQP.BasicProperties props =
                new AMQP.BasicProperties().builder().correlationId(corId).replyTo(replyQueueName).build();

        channel.basicPublish("", Constant.RabbitMQ.REQUEST_QUEUE_NAME, props, SerializationUtils.serialize(transaction));
        log.info("Transaction sending...");

        final CompletableFuture<Response> completer = new CompletableFuture<>();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info("Response received.");
            if (delivery.getProperties().getCorrelationId().equals(corId)) {
                completer.complete(SerializationUtils.deserialize(delivery.getBody()));
                log.info("Completer completed");
            }
        };
        String cTag = channel.basicConsume(replyQueueName, autoAck, deliverCallback,
                consumerTag -> {
                });
        log.info("Waiting for reply...");
        Response result = completer.get();
        channel.basicCancel(cTag);
        return result;
    }

    @Override
    public void close() throws IOException {
        connection.close();
        log.info("Client close.");
    }
}
