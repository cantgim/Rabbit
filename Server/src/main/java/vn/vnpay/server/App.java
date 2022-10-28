package vn.vnpay.server;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    public static final String HOST_NAME = "localhost";
    public static final String QUEUE_NAME = "transaction_queue";
    public static void main(String[] args) {
        try {
            new Worker(HOST_NAME, QUEUE_NAME);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
