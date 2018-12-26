package com.gallego.money.hex.model.checkout.command;

import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;
import com.gallego.money.hex.model.checkout.vo.CheckoutCreditCustomInteresRequest;
import com.gallego.money.hex.model.ledger.command.LedgerDebit;
import com.gallego.money.hex.model.ledger.vo.LedgerDebitRequest;
import com.gallego.money.util.Context;

import java.util.Arrays;

@Command
public class CheckoutCustomInterestCredit {

    public void execute(CheckoutCreditCustomInteresRequest request) {
        Credit credit = Context.gateway.findCreditBy(request.getCreditId());
        Product product = new Product(request.getAmount(),request.getDescription(), request.getShares());
        product.setCreditId(request.getCreditId());
        product.setInterest(request.getInterest());
        product.setDebt(request.getAmount());
        credit.addProduct(product);
        Context.gateway.persist(credit);
        new LedgerDebit().execute(new LedgerDebitRequest(request.getCreditId(),request.getAmount()));
    }

}

