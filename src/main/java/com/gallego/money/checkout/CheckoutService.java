package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CheckoutService {

    CheckoutProcess checkoutProcess;

    public CheckoutService (CheckoutProcess checkoutProcess){
        this.checkoutProcess = checkoutProcess;
    }

    public void payWithCredit(Products products, Long creditId, Integer shares, BigDecimal amount)  {
        setCreditInfo(products, creditId, shares, amount);
        checkoutProcess.process(products, creditId, amount);
    }

    public void payWithCash(Products products, Long bankId) {
        BigDecimal amount = products.getTotal();
        checkoutProcess.process(products, bankId, amount);
    }

    private void setCreditInfo(Products products, Long creditId, Integer shares, BigDecimal amount) {
        CreditCard creditCard = Context.gateway.getCreditCardBy(creditId);
        products.setPaymentInfo(creditCard.getId(),shares, amount, creditCard.getInterest());
        Context.gateway.persist(products);
    }
}