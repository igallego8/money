package com.gallego.money.model;

public interface Gateway {
    CreditCard getCreditCard();

    Ledger getLedger(Integer id);
}
