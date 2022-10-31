//package vn.vnpay.client;
//
//import lombok.extern.slf4j.Slf4j;
//import vn.vnpay.client.constant.Constant;
//import vn.vnpay.server.domain.Transaction;
//
//import java.util.Date;
//
//@Slf4j
//public class App {
//    public static void main(String[] args) {
//        try (Client client = new Client(Constant.RabbitMQ.HOST_NAME);) {
//            Transaction transaction = new Transaction(1, 1, "donald", 100, new Date());
//            log.info("[x] Validate result :: {}", client.call(transaction));
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//}
