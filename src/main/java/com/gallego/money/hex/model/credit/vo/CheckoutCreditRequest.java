package com.gallego.money.hex.model.credit.vo;

import java.math.BigDecimal;

public class CheckoutCreditRequest {

    private BigDecimal amount;
    private String description;
    private Long creditId;
    private Integer shares;

    public CheckoutCreditRequest(BigDecimal amount, String description, Long creditId, Integer shares) {
        this.amount = amount;
        this.description = description;
        this.creditId = creditId;
        this.shares = shares;
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
}
