package vn.vnpay.api.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import vn.vnpay.api.service.TransactionService;
import vn.vnpay.api.service.impl.TransactionServiceImpl;
import vn.vnpay.server.domain.Response;
import vn.vnpay.server.domain.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            log.info("Begin transaction post request.");
            String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (requestBody.isEmpty()) {
                log.info("Invalid request.");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            log.info("Raw request data {}", requestBody);
            transactionService = new TransactionServiceImpl();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Response response =
                    transactionService.createTransaction(gson.fromJson(requestBody,
                            Transaction.class));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            OutputStream os = resp.getOutputStream();
            os.write(gson.toJson(response).getBytes());
            os.flush();
            log.info("Response sent.");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
