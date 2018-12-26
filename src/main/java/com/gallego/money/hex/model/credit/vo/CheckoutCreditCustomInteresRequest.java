package com.gallego.money.hex.model.credit;

import java.math.BigDecimal;

public class CheckoutCreditCustomInteresRequest {

    private BigDecimal amount;
    private String description;
    private Long creditId;
    private Integer shares;
    private Float interest;

    public CheckoutCreditCustomInteresRequest(BigDecimal amount, String description, Long creditId, Integer shares, Float interest) {
        this.amount = amount;
        this.description = description;
        this.creditId = creditId;
        this.shares = shares;
        this.interest = interest;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreditId() {
        return creditId;
    }

    public Integer getShares() {
        return shares;
    }

    public Float getInterest() {
        return interest;
    }
}
