package com.gallego.money.integration;

import com.gallego.money.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MockGateway implements Gateway {

    List<Credit> credits = new ArrayList<>();
    List<Product> productsPersisted = new ArrayList<>();
    List<Transac> transactsPersisted = new ArrayList<>();

    List<Ledger> ledgersPersisted = new ArrayList<>();


    public MockGateway (){
        System.out.println(" Creating new MockGateway");
    }

    @Override
    public Ledger getLedger(Long id) {
        return ledgersPersisted.stream().filter(l -> l.getAssetId().equals(id)).findFirst().orElse(new Ledger(0L));
    }

    @Override
    public Credit getCreditCardBy(Long id) {
        System.out.println(" Credit Cards list size :: " + credits.size());
        credits.stream().forEach(c -> System.out.println("Credit Card :: "+ c.getId()));
        return credits.stream().filter(c-> c.getId().equals(id)).findFirst().orElseThrow(()->new NullPointerException("Credit not found"));
    }

    @Override
    public void persist(Products products) {
        Iterator<Product> it = products.iterator();
        while(it.hasNext()){
            productsPersisted.add(it.next());
        }
    }

    @Override
    public void persist(Transac transac) {
        transactsPersisted.add(transac);
    }

    @Override
    public void persist(Ledger ledger) {
        ledgersPersisted.add(ledger);
    }

    @Override
    public void persist(Credit credit) {
        credits.add(credit);
    }

    @Override
    public Products getProductsBy(Long creditId) {
      return  new Products(productsPersisted.stream().filter(p -> p.getCreditId().equals(creditId)).collect(Collectors.toList()));
    }

    @Override
    public List<Credit> fetchCredits() {
        return credits;
    }

    @Override
    public Products fetchProducts() {
        return  new Products(productsPersisted.stream().collect(Collectors.toList()));
    }
}
