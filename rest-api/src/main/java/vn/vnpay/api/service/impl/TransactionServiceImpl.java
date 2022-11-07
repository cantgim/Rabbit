package vn.vnpay.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.api.service.TransactionService;
import vn.vnpay.common.domain.Response;
import vn.vnpay.common.domain.Transaction;
import vn.vnpay.common.mq.ChannelPool;
import vn.vnpay.common.mq.ChannelPoolable;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Override
    public Response createTransaction(Transaction transaction) throws IOException, TimeoutException,
            ExecutionException, InterruptedException {
        log.info("Begin createTransaction.");
        ChannelPoolable channel = ChannelPool.getInstance();
        // TODO: publish message
        return Response.SUCCESS;
    }
}
