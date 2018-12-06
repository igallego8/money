package com.gallego.money.entity;

import java.math.BigDecimal;

public class Credit {
    private BigDecimal quota;
    private BigDecimal debt;
    private float interest;

    private Long id;

    public Credit(BigDecimal quota, BigDecimal debt) {
        this.quota = quota;
        this.debt = debt;
        id = 1 + (long)(Math.random() * 10000);
    }

    public Credit(BigDecimal quota, Float interest) {
        this.quota = quota;
        this.debt = BigDecimal.ZERO;
        this.interest = interest;
        id = 1 + (long)(Math.random() * 10000);
    }

    public Long getId() {
        return id;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public Float getInterest() {
        return interest;
    }

    public BigDecimal getDebt(){
        return debt;
    }
}
