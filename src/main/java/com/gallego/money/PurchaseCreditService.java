package com.gallego.money;

import com.gallego.money.checkout.CheckoutService;
import com.gallego.money.checkout.DefaultCheckoutProcess;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;
import com.gallego.money.payment.PaymentService;

import java.math.BigDecimal;
import java.util.Arrays;

public class PurchaseCreditService {
    public Credit purchase(Long creditId, BigDecimal amount, int shares, Float interest) {
        PaymentService paymentService = new PaymentService();
        paymentService.pay(creditId, amount);

        Credit credit = new Credit(amount, amount);
        credit.setInterest(interest);
        Context.gateway.persist(credit);
        Product product = new Product(amount);
        product.setCreditId(creditId);
        product.setDebt(amount);

        CheckoutService checkoutService = new CheckoutService(new DefaultCheckoutProcess());
        checkoutService.payWithCredit(new Products(Arrays.asList(product)),credit.getId(),shares,amount);
        return credit;

    }

}
