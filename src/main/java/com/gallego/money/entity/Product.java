package com.gallego.money.entity;

import java.math.BigDecimal;

public class Product extends  Entity {


    private BigDecimal amount;
    private BigDecimal debt;
    private Long creditId;
    private Float interest;
    private Integer shares;
    private Integer sharesPaid=0;
    private String description = this.hashCode()+"";

    public Product(BigDecimal amount) {
        this.amount = amount;
    }

    public Product(BigDecimal amount, String description, Integer shares) {
        this.amount = amount;
        this.description = description;
        this.shares = shares;

    }


    public void deductDebt(BigDecimal d){
        if (debt.compareTo(d) == -1){
            throw new OverDeductionException("Deduction is grater than  debt");
        }
        debt=  debt.subtract(d);
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
         this.amount=amount;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public Float getInterest() {
        return interest;
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

    public Long getCreditId() {
        return creditId;
    }

    public boolean hasDebt() {
       return debt.compareTo(BigDecimal.ZERO) > 0 ;
    }

    public void toCredit(BigDecimal subtract) {
        debt=debt.subtract(subtract);
    }

    public Integer getSharesPaid() {
        return sharesPaid;
    }

    public void incrementSharePaid() {
        sharesPaid++;
    }

    public void payOut() {
        debt = BigDecimal.ZERO;
        sharesPaid = shares;
    }

    public Integer percentagePaid(){
       return 100-debt.multiply(BigDecimal.valueOf(100)).divide(amount).intValue();
    }

    public String getDescription() {
        return description;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public void setDebt(BigDecimal debt) {
       this.debt= amount.compareTo(debt) > -1 ? debt : amount;
    }

    public boolean equals(Object o){
        if (o instanceof  Product){
            return id.equals(((Product) o).getId());
        }
        return false;
    }
}
