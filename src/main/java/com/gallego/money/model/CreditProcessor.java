package com.gallego.money.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditProcessor {


    CreditCard creditCard;
    public CreditProcessor(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public BigDecimal nextPayment() {
        return new BigDecimal(40.00);
    }
}
