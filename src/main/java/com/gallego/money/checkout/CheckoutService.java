package com.gallego.money.checkout;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Products;

import java.math.BigDecimal;

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
        Credit credit = Context.gateway.getCreditCardBy(creditId);
        products.setPaymentInfo(credit.getId(),shares, amount, credit.getInterest());
        Context.gateway.persist(products);
    }
}
