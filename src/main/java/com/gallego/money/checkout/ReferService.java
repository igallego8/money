package com.gallego.money.checkout;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;
import com.gallego.money.model.CreditReferRequest;
import com.gallego.money.payment.PaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReferService {

    PaymentService paymentService = new PaymentService();

    public void defer(CreditReferRequest request) {
        Products products = Context.gateway.getProductsBy(request.creditId);
        BigDecimal totalDebt = products.getTotalDebt();
        paymentService.pay(request.creditId,totalDebt);

        Product product = new Product(totalDebt, "Referred",request.shares);
        List<Product> products1 = new ArrayList<>();
        products1.add(product);
        products = new Products(products1);
        setCreditInfo(products,request.creditId,request.shares,totalDebt,request.interest);
    }

    private void setCreditInfo(Products products, Long creditId, Integer shares, BigDecimal amount, Float interest) {
        Credit credit = Context.gateway.getCreditCardBy(creditId);
        products.setPaymentInfo(credit.getId(),shares, amount, interest);
        Context.gateway.persist(products);
    }
}

