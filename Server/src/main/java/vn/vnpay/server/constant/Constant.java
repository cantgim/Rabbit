package vn.vnpay.server.constant;

public class Constant {
    public interface RabbitMQ {
        String HOST_NAME = "localhost";
        String REQUEST_QUEUE_NAME = "transaction_queue";
    }
    public interface Redis {
        String HOST_NAME = "localhost";
        int MAX_TOTAL = 20;
        int MAX_IDLE = 10;
        int MIN_IDLE = 5;
        int MIN_EVICTABLE_IDLE_SEC = 60;
        int TIME_BETWEEN_EVICTION_RUNS_SEC = 30;
        boolean BLOCK_WHEN_EXHAUSTED = true;
    }
    public interface RedisKey {
        String TRANSACTION_ID = "transaction_id";

    }
}
