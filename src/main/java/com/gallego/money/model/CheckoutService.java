package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CheckoutService {

    public void payWithCredit(Products products, Long creditId, Integer shares, BigDecimal amount)  {
        CreditCard creditCard = Context.gateway.getCreditCardBy(creditId);
        products.setPaymentInfo(creditCard.getId(),shares, amount, creditCard.getInterest());

        Transac transac = new Transac(amount);
        transac.setDate(new Date());
        products.setTransactionInfo(transac.getId());

        Ledger ledger = Context.gateway.getLedger(creditId);
        ledger.debit(amount);
        Context.gateway.persist(transac);
        Context.gateway.persist(products);
    }
}
