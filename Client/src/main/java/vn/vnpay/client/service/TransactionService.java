package vn.vnpay.client.service;

import vn.vnpay.server.domain.Response;
import vn.vnpay.server.domain.Transaction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface TransactionService {
    Response createTransaction(Transaction transaction) throws IOException, TimeoutException,
            ExecutionException, InterruptedException;
}
