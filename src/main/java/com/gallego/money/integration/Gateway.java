package com.gallego.money.integration;

import com.gallego.money.hex.model.entity.*;
import money.SavingAccount;

import java.util.List;

public interface Gateway {

    Ledger getLedger(Long id);

    Credit findCreditBy(Long id);

    void persist(Product product);
    void delete(Product product);
    void update(Product product);
    List<Product> fetchProducts();
    List<Product> getProductsBy(Long creditId);



    void persist(Transac transac);

    void persist(Ledger ledger);
    List<Ledger> fetchLedger();


    void persist(Credit credit);
    List<Credit> fetchCredits();
    void update(Credit credit);


    void persist(User user);

    User findUserBy(String userName);

    User findUserBy(Long userId);

    AppFunction findAppFunctionBy(String name);

    void persist(AppFunction function);

    List<License> findLicensesByUserAndFunction(User user, AppFunction function);

    void persist(License license);

    void persist(SavingAccount savingAccount);

    SavingAccount getSavingAccount(Long savingAccountId);

    void update(SavingAccount savingAccount);

}
