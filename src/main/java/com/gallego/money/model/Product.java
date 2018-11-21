package com.gallego.money.model;

import java.math.BigDecimal;

public class Product {


    private BigDecimal amount, debt;
    private Long creditId;
    private Float interest;
    private Integer shares;

    public Product(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public Float getInterest() {
        return interest;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public BigDecimal getDebt() {
       return  debt;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getShares() {
        return shares;
    }


    public void setTransactionId(Long transactionId) {
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getCreditId() {
        return creditId;
    }

    public boolean hasDebt() {
       return debt.compareTo(new BigDecimal(0)) > 0 ;
    }
}
