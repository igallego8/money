package com.gallego.money.hex.model.checkout.command;

import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.hex.model.checkout.vo.CheckoutCreditRequest;
import com.gallego.money.hex.model.ledger.command.LedgerDebit;
import com.gallego.money.hex.model.ledger.vo.LedgerDebitRequest;
import com.gallego.money.util.Context;

public class CheckoutCredit {

    public void execute(CheckoutCreditRequest request) {
        Credit credit = Context.gateway.findCreditBy(request.getCreditId());
        Product product = new Product(request.getAmount(),request.getDescription(),request.getShares());
        product.setDebt(request.getAmount());
        product.setCreditId(request.getCreditId());
        product.setInterest(credit.getInterest());
        credit.addProduct(product);
        Context.gateway.persist(credit);
        new LedgerDebit().execute(new LedgerDebitRequest(request.getCreditId(),request.getAmount()));
    }
}

