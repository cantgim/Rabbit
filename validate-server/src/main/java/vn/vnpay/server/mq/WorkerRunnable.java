package vn.vnpay.server.mq;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.common.mq.ChannelPool;
import vn.vnpay.common.mq.ChannelPoolable;

/**
 * WorkerCallable get channel from pool then execute task
 */
@Slf4j
public class WorkerRunnable implements Runnable {
    @Override
    public void run() {
        try {
            ChannelPoolable channel = ChannelPool.getInstance();
            // channel.queueSetup();
            // channel.queueConsume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}