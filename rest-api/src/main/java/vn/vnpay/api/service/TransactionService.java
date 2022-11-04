package vn.vnpay.api.service;

import vn.vnpay.common.domain.Response;
import vn.vnpay.common.domain.Transaction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface TransactionService {
    Response createTransaction(Transaction transaction) throws IOException, TimeoutException,
            ExecutionException, InterruptedException;
}
