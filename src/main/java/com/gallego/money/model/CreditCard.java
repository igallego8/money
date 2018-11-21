package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Random;

public class CreditCard {
    private BigDecimal quota;
    private BigDecimal debt;
    private float interest;

    private Long id;

    public CreditCard(BigDecimal quota, BigDecimal debt) {
        this.quota = quota;
        this.debt = debt;
        id = 1 + (long)(Math.random() * 10000);
    }

    public Long getId() {
        return id;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public float getInterest() {
        return interest;
    }
}
