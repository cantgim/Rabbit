package vn.vnpay.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class Transaction implements Serializable {
    private long transactionId;
    private long trace;
    private String name;
    private double amount;
    private Date payDate;
}
