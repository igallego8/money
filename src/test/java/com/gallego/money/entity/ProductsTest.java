package com.gallego.money.entity;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsTest {


    @Test
    public  void givenProduct_WhenNextTotalCharge_ThenNextPaymentValidated(){
        List<Product> products = new ArrayList<>();
        Product product = new Product(BigDecimal.valueOf(10),"Product1",10);
        products.add(product);
        product.setDebt(BigDecimal.valueOf(10));
        product.setInterest(1f);
        Products p = new Products(products);
        Assert.assertTrue("Wring next total charge",p.getNextTotalCharge().compareTo(BigDecimal.valueOf(1.1))==0);
    }


    @Test
    public  void givenProduct_WhenTotalDebt_ThenTotalDebtValidated(){
        List<Product> products = new ArrayList<>();
        Product product = new Product(BigDecimal.valueOf(10), "Product1",10);
        product.setDebt(BigDecimal.valueOf(10));
        products.add(product);
        product.setInterest(1f);
        Products p = new Products(products);
        Assert.assertTrue("Wrong total debt" ,p.getTotalDebt().compareTo(BigDecimal.valueOf(10.1))==0);
    }


    @Test
    public  void givenCreditPayment_WhenProductIsPaid_ThenCreditInfoIsPopulated(){

        List<Product> products = new ArrayList<>();
        Product product = new Product(BigDecimal.valueOf(10) ,"Product1",10);
        products.add(product);
        product.setDebt(BigDecimal.valueOf(10));
        product.setInterest(1f);
        Products p = new Products(products);
        p.setPaymentInfo(1L,24,BigDecimal.valueOf(100),1f);
    }
}
