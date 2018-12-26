package com.gallego.money.hex.model.credit;

import java.math.BigDecimal;

public class PurchaseCreditRequest {
    private Long creditId;
    private BigDecimal amount;
    private Integer cutoffDay;
    private float interest;
    private Integer shares;

    public PurchaseCreditRequest(Long creditId, BigDecimal amount, Integer cutoffDay, float interest, Integer shares) {
        this.creditId = creditId;
        this.amount = amount;
        this.cutoffDay = cutoffDay;
        this.interest = interest;
        this.shares = shares;
    }

    public Long getCreditId() {
        return creditId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getCutoffDay() {
        return cutoffDay;
    }

    public float getInterest() {
        return interest;
    }

    public Integer getShares() {
        return shares;
    }
}
