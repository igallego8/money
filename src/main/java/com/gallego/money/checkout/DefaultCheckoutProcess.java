package com.gallego.money.checkout;

import com.gallego.money.model.Context;
import com.gallego.money.model.Ledger;
import com.gallego.money.model.Products;
import com.gallego.money.model.Transac;

import java.math.BigDecimal;
import java.util.Date;

public class DefaulCheckoutProcess implements CheckoutProcess {

    @Override
    public void process(Products products, Long assetId, BigDecimal amount) {
        processTransaction(products, amount);
        processLedger(assetId, amount);
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

