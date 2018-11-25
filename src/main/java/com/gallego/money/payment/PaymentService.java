package com.gallego.money.payment;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Ledger;
import com.gallego.money.entity.Products;

import java.math.BigDecimal;


public class PaymentService {

    public BigDecimal pay(Long creditId, BigDecimal amount) {
        Products products = Context.gateway.getProductsBy(creditId);
        Ledger ledger = Context.gateway.getLedger(creditId);
        ledger.credit(amount);
        return products.credit(amount);
    }
}
