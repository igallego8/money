package com.gallego.money.hex.model.credit.vo;

public class ReferCreditRequest {
    private Long creditId;
    private Integer shares;
    private Float interest;

    public ReferCreditRequest(Long creditId, Integer shares, Float interest) {
        this.creditId = creditId;
        this.shares = shares;
        this.interest = interest;
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
