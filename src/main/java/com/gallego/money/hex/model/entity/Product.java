package com.gallego.money.hex.model.entity;

import com.gallego.money.exception.OverDeductionException;
import com.gallego.money.util.date.DateChangeHandler;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product extends  Entity {


    private BigDecimal amount;
    private BigDecimal debt = BigDecimal.ZERO;
    private Long creditId;
    private Float interest ;
    private Integer shares;
    private Integer sharesPaid=0;
    private String description = this.hashCode()+"";
    private LocalDate date;


    public Product(BigDecimal amount, String description, Integer shares) {
        this.amount = amount;
        this.description = description;
        this.shares = shares;
        date = LocalDate.now();

    }

    public void changeDate(DateChangeHandler handler, int offset){
        date = handler.process(date, offset);
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
       return 100-(debt.multiply(BigDecimal.valueOf(100)).intValue())/amount.intValue();
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

    public LocalDate getDate() {
        return date;
    }

    public boolean isBeforeTo(LocalDate date){
        return this.date.isBefore(date);
    }

    public BigDecimal calculateInterestCharge() {
        return debt.multiply(interest.compareTo(0f)>0 ?new BigDecimal(interest / 100):new BigDecimal(0));
    }


    public BigDecimal getShareAmount() {
       return debt.divide(new BigDecimal((shares - sharesPaid) > 0 ? shares - sharesPaid : 1), BigDecimal.ROUND_HALF_UP);
    }


    public void creditShareAmount() {
        toCredit(getShareAmount());
        incrementSharePaid();
    }

    public void creditWith(BigDecimal amount) {
        toCredit(amount.subtract(debt));
        incrementSharePaid();
    }
}
