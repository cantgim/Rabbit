package vn.vnpay.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum Response implements Serializable {

    SUCCESS("00", "Success"),
    INVALID_TRANSACTION("01", "Transaction is invalid."),
    TIMEOUT_FAILURE("02", "Request transaction timeout."),
    AMOUNT_FAILURE("03", "Amount is invalid."),
    IDENTITY_FAILURE("04", "TransactionID is invalid.");

    private final String code;
    private final String message;
}
