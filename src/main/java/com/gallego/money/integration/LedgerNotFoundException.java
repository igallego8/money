package com.gallego.money.integration;

public class LedgerNotFoundException  extends RuntimeException {
    public LedgerNotFoundException(String s) {
        super(s);
    }
}
