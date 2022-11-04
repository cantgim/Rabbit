package vn.vnpay.common.constant;

/**
 * TODO(cantgim): move config properties to file
 */
public class Constant {
    public interface RabbitMQ {
        String HOST_NAME = "localhost";
        String REQUEST_QUEUE_NAME = "transaction_queue";
        boolean DURABLE = false;
        boolean EXCLUSIVE = false;
        boolean AUTO_DELETE = false;
        int PREFETCH_COUNT = 1;
    }

    public interface Redis {
        String HOST_NAME = "localhost";
        int PORT = 6379;
        int MAX_TOTAL = 20;
        int MAX_IDLE = 10;
        int MIN_IDLE = 5;
        int MIN_EVICTABLE_IDLE_SEC = 60;
        int TIME_BETWEEN_EVICTION_RUNS_SEC = 30;
        boolean BLOCK_WHEN_EXHAUSTED = true;
    }

    public interface TransactionKey {
        String trace = "trace";
    }

    public interface Transaction {
        int TIMEOUT_MIN = 5;
    }
}
