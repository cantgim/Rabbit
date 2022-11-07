package vn.vnpay.server.mq;

import com.rabbitmq.client.Channel;
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
        // get channel pool
        ChannelPoolable channel = ChannelPool.getInstance();
        // execute task

        // return callable
        return Response.SUCCESS;
    }
}