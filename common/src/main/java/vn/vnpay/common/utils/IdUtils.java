package vn.vnpay.common.utils;

import com.twitter.snowflake.sequence.IdGenerator;
import com.twitter.snowflake.support.ElasticIdGeneratorFactory;

import java.util.concurrent.TimeUnit;

public class IdUtils {
    private static final int TIME_BITS = 41;
    private static final int WORKER_BITS = 12;
    private static final int SEQUENCE_BITS = 10;
    private static final long EPOCH_TIMESTAMP = 1415333514000L;
    private final IdGenerator idGenerator;

    private IdUtils() {
        ElasticIdGeneratorFactory factory = new ElasticIdGeneratorFactory();
        factory.setTimeBits(TIME_BITS);
        factory.setWorkerBits(WORKER_BITS);
        factory.setSeqBits(SEQUENCE_BITS);
        factory.setTimeUnit(TimeUnit.MILLISECONDS);
        factory.setEpochTimestamp(EPOCH_TIMESTAMP);
        idGenerator = factory.create(1L);
    }

    private static final IdUtils INSTANCE = new IdUtils();

    public static IdUtils getInstance() {
        return INSTANCE;
    }

    public long next() {
        //TODO(cantgim): check NULL
        return idGenerator.nextId();
    }
}
