package com.gallego.money;

import com.gallego.money.checkout.CheckoutService;
import com.gallego.money.checkout.DefaultCheckoutProcess;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.model.BuyCreditRequest;
import com.gallego.money.payment.PaymentService;

import java.math.BigDecimal;

public class PurchaseCreditService {

    public Credit purchase(Long creditId, BigDecimal amount, int shares, Float interest) {
        PaymentService paymentService = new PaymentService();
        paymentService.pay(creditId, amount);

        Credit credit = new Credit(amount, amount);
        credit.setInterest(interest);
        Context.gateway.persist(credit);

        BuyCreditRequest request = new BuyCreditRequest();
        request.amount = amount;
        request.shares = shares;
        request.creditId = credit.getId();
        request.description = "Debt purchased";

        CheckoutService checkoutService = new CheckoutService(new DefaultCheckoutProcess());
        checkoutService.payWithCredit(request);
        return credit;

    }

}
