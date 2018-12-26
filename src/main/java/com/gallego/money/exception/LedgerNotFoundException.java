package com.gallego.money.exception;

public class LedgerNotFoundException  extends RuntimeException {
    public LedgerNotFoundException(String s) {
        super(s);
    }
}
