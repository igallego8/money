package com.gallego.money.model;

public interface Gateway {

    Ledger getLedger(Long id);

    CreditCard getCreditCardBy(Long id);

    void persist(Products products);

    void persist(Transac transac);

    void persist(Ledger ledger);

    void persist(CreditCard creditCard);

    Products getProductsBy(Long creditId);
}
