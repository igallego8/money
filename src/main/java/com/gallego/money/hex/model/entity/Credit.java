package com.gallego.money.hex.model.entity;

import com.gallego.money.exception.InsufficientFundsException;
import com.gallego.money.util.TimeContext;
import com.gallego.money.util.Validations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Credit extends  Entity {

    private BigDecimal quota;
    private BigDecimal debt;
    private float interest;
    private Integer cutoffDay;
    private List<Product> products= new ArrayList<>();
    private Ledger ledger;


    public  void addProduct(Product product){
        Validations.validateArgument(Objects.isNull(product));
        product.setCreditId(id);
        products.add(product);
    }


    public void setPaymentInfo(Long creditId, Integer shares, BigDecimal amount, Float interest) {
        BigDecimal total = getTotal();
        products.forEach(p -> {
            BigDecimal d = amount.multiply(p.getAmount().divide(total, 3));
            p.setDebt(d);
            p.setInterest(interest);
            p.setShares(shares);
            p.setCreditId(creditId);
        });
    }

    public BigDecimal getTotal() {
        return products.stream().filter(p -> p.getAmount().compareTo(BigDecimal.ZERO) > 0).map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Credit(BigDecimal quota, BigDecimal debt,Float interest,Integer cutoffDay) {
        this.quota = quota;
        this.debt = debt;
        this.interest = interest;
        this.cutoffDay = cutoffDay;
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

    public void  setCutoffDay(Integer cutoffDay){
        this.cutoffDay = cutoffDay;
    }

    public LocalDate nextCutOffDate(){
        LocalDate localDate = TimeContext.timeHandler.today();
       if ( localDate.getDayOfMonth() >= cutoffDay){
            localDate= localDate.plusMonths(1);
       }
        localDate = localDate.withDayOfMonth(cutoffDay );
        return localDate;
    }


    public LocalDate prevCutOffDate(){

        Set d = new HashSet<>();
        List<String> l = new ArrayList<>();
        l.addAll(d);
        LocalDate localDate = TimeContext.timeHandler.today();
        int dayOfMonth = localDate.getDayOfMonth();
        if ( dayOfMonth < cutoffDay){
            localDate= localDate.minusMonths(1);
            localDate = localDate.plusDays(cutoffDay-dayOfMonth);
        }else {
            localDate = localDate.minusDays(dayOfMonth - cutoffDay);
        }
        return localDate;
    }

    public Integer getCutoffDay() {
        return cutoffDay;
    }

    public List<Product> getProducts() {
        return products;
    }


    public BigDecimal calculateInterest(){
        List<Product> it=products;
        BigDecimal amount = BigDecimal.ZERO;
        for (Product product:it){
            if (product.isBeforeTo(prevCutOffDate())) {
                amount  = amount.add(product.calculateInterestCharge());
            }
        }
        return amount;
    }

    private BigDecimal payShare(BigDecimal amount, BigDecimal nextTotalCharge) {
        products.stream().filter(Product::hasDebt).forEach(Product::creditShareAmount);
        BigDecimal extra = amount.subtract(nextTotalCharge);
        for (Product p:products.stream().filter(Product::hasDebt).collect(Collectors.toList())){
            if (p.hasDebt() && extra.compareTo(BigDecimal.ZERO) > 0){
                if ( extra.compareTo(p.getDebt()) > -1){
                    p.creditWith(extra);
                }else{
                    p.toCredit(extra);
                }
                if(extra.compareTo(BigDecimal.ZERO) == 0){
                    break;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal credit(BigDecimal amount) {
        BigDecimal totalDebt = getTotalDebt();
        BigDecimal nextTotalCharge = getNextTotalCharge();
        if (amount.compareTo(totalDebt) > -1){
            return payOut(amount, totalDebt);
        }else if (amount.compareTo(nextTotalCharge) > -1){
            return payShare(amount, nextTotalCharge);
        }else{
            throw new InsufficientFundsException("Insufficient funds to pay credit");
        }
    }


    private BigDecimal payOut(BigDecimal amount, BigDecimal totalDebt) {
        products = new ArrayList<>(); //.stream().filter(Product::hasDebt).forEach(Product::payOut);
        return amount.subtract(totalDebt);
    }

    public BigDecimal getNextTotalCharge(){
        return calculateShareAmount().add(calculateInterest()).setScale(2,BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getTotalDebt() {
        return calculateTotalDebt().add(calculateInterest()).setScale(2,BigDecimal.ROUND_HALF_UP);
    }


    private BigDecimal calculateTotalDebt() {
        return  products.stream().map(p -> p.getDebt()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateShareAmount() {
        return products.stream().map(p -> p.getDebt().divide(new BigDecimal((p.getShares() - p.getSharesPaid()) > 0 ? p.getShares() - p.getSharesPaid() : 1), BigDecimal.ROUND_HALF_UP)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }
}
