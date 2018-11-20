package com.gallego.money.model;

import java.math.BigDecimal;

public class CreditProcessor {


    CreditCard creditCard;
    public CreditProcessor(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public BigDecimal nextPayment() {
        return new BigDecimal(40.00);
    }
}
