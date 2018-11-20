package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.List;

public class CreditCardPayment {
    private CreditCard creditCard;

    public CreditCardPayment(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void pay(List<Product> products, int shares) {

    }
}
