package vn.vnpay.api.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import vn.vnpay.api.service.TransactionService;
import vn.vnpay.api.service.impl.TransactionServiceImpl;
import vn.vnpay.common.constant.Constant;
import vn.vnpay.common.domain.Response;
import vn.vnpay.common.domain.Transaction;
import vn.vnpay.common.mq.ChannelPool;
import vn.vnpay.common.utils.IdUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@WebServlet(urlPatterns = "/api/v0/transaction")
public class TransactionController extends HttpServlet {

    private TransactionService transactionService;

    private static final Gson GSON =
            new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void init() {
        // init queue connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.RabbitMQ.HOST_NAME);

        try (Connection connection = factory.newConnection()) {
            new ChannelPool(connection);
        } catch (Exception e) {
            log.error("Stack trace", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            IdUtils idUtils = IdUtils.getInstance();
            MDC.put("token", String.valueOf(idUtils.next()));

            log.info("Begin transaction post request.");
            String requestBody =
                    req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (requestBody.isEmpty()) {
                log.info("Invalid request.");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            log.info("Raw request data {}", requestBody);
            transactionService = new TransactionServiceImpl();
            Response response = transactionService.createTransaction(
                    GSON.fromJson(requestBody, Transaction.class));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            OutputStream os = resp.getOutputStream();
            os.write(GSON.toJson(response).getBytes());
            os.flush();
            log.info("Response sent.");
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
