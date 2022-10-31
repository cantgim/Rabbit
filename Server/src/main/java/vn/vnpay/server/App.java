package vn.vnpay.server;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.server.constant.Constant;
import vn.vnpay.server.mq.Worker;

@Slf4j
public class App {
    public static void main(String[] args) {
        try {
            new Worker(Constant.RabbitMQ.HOST_NAME, Constant.RabbitMQ.REQUEST_QUEUE_NAME);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
