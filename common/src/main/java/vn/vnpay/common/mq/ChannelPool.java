package vn.vnpay.common.mq;

import com.rabbitmq.client.Connection;
import stormpot.Pool;
import stormpot.Timeout;

import java.util.concurrent.TimeUnit;

public class ChannelPool {
    private static Pool<ChannelPoolable> pool;

    public ChannelPool(Connection connection) {
        pool = Pool.from(new ChannelAllocator(connection)).build();
    }

    public static ChannelPoolable getInstance() throws InterruptedException {
        return pool.claim(new Timeout(1, TimeUnit.SECONDS));
    }

    public void close() {
        if (pool != null) {
            pool.shutdown();
        }
    }
}
