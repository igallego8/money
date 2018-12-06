package com.gallego.money.entity;

public class OverDeductionException extends RuntimeException {
    public OverDeductionException(String s) {
        super(s);
    }
}
