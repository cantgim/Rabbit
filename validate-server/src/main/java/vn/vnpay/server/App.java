package vn.vnpay.server;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.common.constant.Constant;
import vn.vnpay.server.mq.ChannelPool;

import java.util.Arrays;

@Slf4j
public class App {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Constant.RabbitMQ.HOST_NAME);
            try (Connection connection = factory.newConnection()) {
                new ChannelPool(connection);
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
