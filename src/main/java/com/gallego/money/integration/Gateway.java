package com.gallego.money.integration;

import com.gallego.money.entity.CreditCard;
import com.gallego.money.entity.Ledger;
import com.gallego.money.entity.Products;
import com.gallego.money.entity.Transac;

import java.util.List;

public interface Gateway {

    Ledger getLedger(Long id);

    CreditCard getCreditCardBy(Long id);

    void persist(Products products);

    void persist(Transac transac);

    void persist(Ledger ledger);

    void persist(CreditCard creditCard);

    Products getProductsBy(Long creditId);

    List<CreditCard> fetchCreditCards();
}
