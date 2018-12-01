package com.gallego.money.integration;

import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Ledger;
import com.gallego.money.entity.Products;
import com.gallego.money.entity.Transac;

import java.util.List;

public interface Gateway {

    Ledger getLedger(Long id);

    Credit getCreditCardBy(Long id);

    void persist(Products products);

    void persist(Transac transac);

    void persist(Ledger ledger);

    void persist(Credit credit);

    void delete(Products products);

    Products getProductsBy(Long creditId);


    List<Credit> fetchCredits();

    Products fetchProducts();
}
