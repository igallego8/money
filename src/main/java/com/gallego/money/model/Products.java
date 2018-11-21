package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Products implements Iterable<Product> {

    private List<Product> products;


    public  Products (List<Product> products){
        this.products = products;
    }

    @Override
    public Iterator<Product> iterator() {
        return new ProductIterator(products);
    }


    public void setPaymentInfo(Long creditId, Integer shares, BigDecimal amount, Float interest) {
        products.stream().forEach(p -> {
            p.setDebt(amount.divide(new BigDecimal(products.size())));
            p.setInterest(interest);
            p.setShares(shares);
            p.setCreditId(creditId);
        });
    }

    public void setTransactionInfo(Long transactionId) {
        products.stream().forEach(p -> p.setTransactionId(transactionId));
    }

    public BigDecimal getNextCharge() {
      return  products.stream().filter(p -> p.hasDebt()).map(p-> getShareAmount(p).add(calculateInterestCharge(p))).reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal getShareAmount(Product p) {
        return p.getDebt().divide(new BigDecimal(p.getShares()));
    }

    private BigDecimal calculateInterestCharge(Product p) {
        return p.getDebt().multiply( p.getInterest().compareTo((float) 0.0)>0? BigDecimal.valueOf(p.getInterest()/100):new BigDecimal(1));
    }
}
