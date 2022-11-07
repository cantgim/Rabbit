package vn.vnpay.server;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.common.constant.Constant;
import vn.vnpay.common.mq.ChannelPool;
import vn.vnpay.common.mq.ChannelPoolable;

import java.util.Arrays;

@Slf4j
public class App {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Constant.RabbitMQ.HOST_NAME);
            try (Connection connection = factory.newConnection()) {
                new ChannelPool(connection);
                ChannelPoolable channel = ChannelPool.getInstance();
                // set up queue
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
