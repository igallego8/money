package com.gallego.money.model;

import java.math.BigDecimal;

public class CheckoutService {
    public void pay() {
        CreditCard creditCard= Context.gateway.getCreditCard();
        ShoppingCart shoppingCart = Context.shoppingCart;
        BigDecimal total = shoppingCart.getTotal();
        Ledger ledger = Context.gateway.getLedger(creditCard.getId());
    }
}
