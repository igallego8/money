package com.gallego.money.model;

import java.math.BigDecimal;


public class PaymentService {
    public BigDecimal pay(Long creditId, BigDecimal amount) {
        Products products = Context.gateway.getProductsBy(creditId);
        return products.credit(amount);

    }
}
