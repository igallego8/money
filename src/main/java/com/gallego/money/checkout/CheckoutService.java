package com.gallego.money.checkout;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;
import com.gallego.money.model.BuyCreditRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    CheckoutProcess checkoutProcess;

    public CheckoutService (CheckoutProcess checkoutProcess){
        this.checkoutProcess = checkoutProcess;
    }

    public void payWithCash(Products products, Long bankId) {
        BigDecimal amount = products.getTotal();
        checkoutProcess.process(products, bankId, amount);
    }

    public void payWithCredit(BuyCreditRequest request) {
        Products p = createProduct(request);
        setCreditInfo(p, request);
        checkoutProcess.process(p, request.creditId, request.amount);
    }

    private Products createProduct(BuyCreditRequest request) {
        List<Product> products = new ArrayList<>();
        products.add(new Product( request.amount, request.description , request.shares));
        return new Products(products);
    }

    private void setCreditInfo(Products products, BuyCreditRequest request) {
        Credit credit = Context.gateway.getCreditCardBy(request.creditId);
        products.setPaymentInfo(credit.getId(),request.shares, request.amount, credit.getInterest());
        Context.gateway.persist(products);
    }
}
