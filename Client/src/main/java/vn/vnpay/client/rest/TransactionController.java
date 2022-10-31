package vn.vnpay.client.rest;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.server.constant.Constant;
import vn.vnpay.client.mq.RabbitMQConfiguration;
import vn.vnpay.client.service.TransactionService;
import vn.vnpay.client.service.impl.TransactionServiceImpl;
import vn.vnpay.server.domain.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@WebServlet(urlPatterns = "/api/v0/transaction")
public class TransactionController extends HttpServlet {

    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        try {
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            transactionService = new TransactionServiceImpl();
            transactionService.createTransaction(new Gson().fromJson(requestBody, Transaction.class));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
