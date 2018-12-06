package com.gallego.money.integration;

import com.gallego.money.entity.*;

import java.util.Iterator;
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

    void persist(User user);

    User findUserBy(String userName);
    User findUserBy(Long userId);

    AppFunction findAppFunctionBy(String name);

    void persist(AppFunction function);

    List<License> findLicensesByUserAndFunction(User user, AppFunction function);

    void persist(License license);

    void update(Products products);

    List<Ledger> fetchLedger();
}
