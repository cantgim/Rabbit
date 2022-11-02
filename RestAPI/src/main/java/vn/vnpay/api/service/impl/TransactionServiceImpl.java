package vn.vnpay.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.api.mq.RabbitMQConfiguration;
import vn.vnpay.api.service.TransactionService;
import vn.vnpay.server.constant.Constant;
import vn.vnpay.server.domain.Response;
import vn.vnpay.server.domain.Transaction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Override
    public Response createTransaction(Transaction transaction) throws IOException, TimeoutException,
            ExecutionException, InterruptedException {
        log.info("Begin createTransaction.");
        try (RabbitMQConfiguration mqConfig =
                     new RabbitMQConfiguration(Constant.RabbitMQ.HOST_NAME)) {
            return mqConfig.call(transaction);
        }
    }
}
