package com.gallego.money.payment;

public class PaymentException extends RuntimeException {

    public PaymentException(String msg) {
        super(msg);
    }
}
