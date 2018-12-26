package com.gallego.money.exception;

public class OverDeductionException extends RuntimeException {
    public OverDeductionException(String s) {
        super(s);
    }
}
