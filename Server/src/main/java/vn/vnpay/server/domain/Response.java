package vn.vnpay.server.domain;

import lombok.Value;

import java.io.Serializable;

@Value
public class Response implements Serializable {
    String code;
    String message;
}
