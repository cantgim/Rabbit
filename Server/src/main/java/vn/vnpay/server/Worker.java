package vn.vnpay.server;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.SerializationUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Slf4j
public class Worker {
    private static final boolean DURABLE = false;
    private static final boolean EXCLUSIVE = false;
    private static final boolean AUTO_DELETE = true;
    private static final int PREFETCH_COUNT = 1;

    private Connection connection;
    private Channel channel;

    public Worker(String hostName, String queueName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        final boolean autoAck = false;
        factory.setHost(hostName);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, DURABLE, EXCLUSIVE, AUTO_DELETE, null);
        channel.queuePurge(queueName);
        channel.basicQos(PREFETCH_COUNT);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            log.info("[x] New transaction received.");
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();
            Transaction transaction = SerializationUtils.deserialize(delivery.getBody());
            channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps,
                    SerializationUtils.serialize(validateTransaction(transaction)));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queueName, autoAck, deliverCallback, cTag -> {
        });
        log.info("[x] Worker listening...");
    }

    private boolean validateTransaction(Transaction transaction) throws IOException {
        log.info("[x] Transaction verifying :: {}", transaction);
        Reader in = new FileReader("transaction.csv");
        Iterable<CSVRecord> records =
                CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);
        for (CSVRecord record : records) {
            
        }
        return false;
    }
}
