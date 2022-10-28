package vn.vnpay.client;

import lombok.extern.slf4j.Slf4j;
import vn.vnpay.server.Transaction;

import java.util.Date;

@Slf4j
public class App {
    private static final String HOST_NAME = "localhost";
    public static void main(String[] args) {
        try (Client client = new Client(HOST_NAME);) {
            Transaction transaction = new Transaction(1, 1, "donald", 100, new Date());
            log.info("[x] Validate result :: {}", client.call(transaction));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
