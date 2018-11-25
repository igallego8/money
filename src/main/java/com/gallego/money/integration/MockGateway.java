package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MockGateway implements Gateway {

    List<CreditCard> creditCards = new ArrayList<>();
    List<Product> productsPersisted = new ArrayList<>();
    List<Transac> transactsPersisted = new ArrayList<>();

    List<Ledger> ledgersPersisted = new ArrayList<>();


    public MockGateway (){
        System.out.println(" Creating new MockGateway");
    }

    @Override
    public Ledger getLedger(Long id) {
        return ledgersPersisted.stream().filter(l -> l.getAssestId() == id).findFirst().orElse(new Ledger(0L));
    }

    @Override
    public CreditCard getCreditCardBy(Long id) {
        System.out.println(" Credit Cards list size :: " + creditCards.size());
        creditCards.stream().forEach(c -> System.out.println("Credit Card :: "+ c.getId()));
        return creditCards.stream().filter(c-> c.getId() == id).findFirst().orElseThrow(()->new NullPointerException("Credit not found"));
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
    public void persist(CreditCard creditCard) {
        creditCards.add(creditCard);
    }

    @Override
    public Products getProductsBy(Long creditId) {
      return  new Products(productsPersisted.stream().filter(p -> p.getCreditId() == creditId).collect(Collectors.toList()));
    }
}
