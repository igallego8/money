package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    public BigDecimal getNextTotalCharge() {
      return  products.stream().filter(p -> p.hasDebt()).map(this::getNextCharge).reduce(new BigDecimal(0), BigDecimal::add);
    }


    public BigDecimal getTotalDebt() {
        return  products.stream().filter(p -> p.hasDebt()).map(this::getTotalCharge).reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal getNextCharge(Product p) {
        return getShareAmount(p).add(calculateInterestCharge(p));
    }

    private BigDecimal getTotalCharge(Product p) {
        return p.getDebt().add(calculateInterestCharge(p));
    }

    private BigDecimal getShareAmount(Product p) {
        return p.getDebt().divide(new BigDecimal((p.getShares()-p.getSharesPaid())>0?p.getShares()-p.getSharesPaid():1),BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calculateInterestCharge(Product p) {
        return p.getDebt().multiply( p.getInterest().compareTo((float) 0)>0? BigDecimal.valueOf(p.getInterest()/100):new BigDecimal(0));
    }

    public BigDecimal credit(BigDecimal amount) {
        BigDecimal totalDebt = getTotalDebt();
        BigDecimal nextTotalCharge = getNextTotalCharge();
        if (amount.compareTo(totalDebt) > -1){
            products.stream().filter(p -> p.hasDebt()).forEach(p-> p.setDebt(BigDecimal.valueOf(0)));
            return amount.subtract(totalDebt);
        }else if (amount.compareTo(nextTotalCharge) > -1){
            products.stream().filter(p -> p.hasDebt()).forEach(p -> {
                p.toDebt(getShareAmount(p));
                p.incrementSharePaid();
            });
            BigDecimal extra = amount.subtract(nextTotalCharge);
            for (Product p:products.stream().filter(p -> p.hasDebt()).collect(Collectors.toList())){
                if (p.hasDebt() ){
                    if ( extra.compareTo(p.getDebt()) > -1){
                        p.toDebt(extra.subtract(p.getDebt()));
                        p.incrementSharePaid();
                    }else{
                        p.toDebt(extra);
                    }
                    if(extra.compareTo(BigDecimal.ZERO) == 0){
                        break;
                    }
                }
            }
            return BigDecimal.ZERO;
        }else{
            throw new PaymentException("Insufficient founds to pay credit");
        }
    }
}
