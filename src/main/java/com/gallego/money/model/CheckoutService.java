package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CheckoutService {

    public void payWithCredit(Products products, Long creditId, Integer shares, BigDecimal amount)  {
        setCreditInfo(products, creditId, shares, amount);
        processTransaction(products, amount);
        processLedger(creditId, amount);
    }

    private void setCreditInfo(Products products, Long creditId, Integer shares, BigDecimal amount) {
        CreditCard creditCard = Context.gateway.getCreditCardBy(creditId);
        products.setPaymentInfo(creditCard.getId(),shares, amount, creditCard.getInterest());
        Context.gateway.persist(products);
    }

    public void payWithCash(Products products, Long bankId) {
        BigDecimal amount = products.getTotal();
        processTransaction(products, amount);
        processLedger(bankId, amount);
    }

    private void processTransaction(Products products, BigDecimal amount) {
        Transac transac = new Transac(amount);
        transac.setDate(new Date());
        products.setTransactionInfo(transac.getId());
        Context.gateway.persist(transac);
    }

    private void processLedger(Long creditId, BigDecimal amount) {
        Ledger ledger = Context.gateway.getLedger(creditId);
        ledger.debit(amount);
    }
}
