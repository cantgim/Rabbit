package vn.vnpay.common.mq;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.common.domain.Response;

import java.util.concurrent.Callable;

/**
 * WorkerCallable get channel from pool then execute task
 */
@Slf4j
public class WorkerCallable implements Callable<Response> {
    @Override
    public Response call() throws Exception {
        ChannelPoolable channel = ChannelPool.getInstance();
        channel.queueSetup();
        channel.queueConsume();

        return Response.SUCCESS;
    }
}