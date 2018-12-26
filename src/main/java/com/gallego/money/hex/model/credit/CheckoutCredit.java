package com.gallego.money.hex.model.credit;

import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.hex.model.entity.Product;
import com.gallego.money.hex.Command;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditRequest;
import com.gallego.money.hex.model.ledger.command.LedgerDebit;
import com.gallego.money.hex.model.ledger.vo.LedgerDebitRequest;
import com.gallego.money.util.Context;

@Command
public class CheckoutCredit {

    public void execute(CheckoutCreditRequest request) {
        Credit credit = Context.gateway.findCreditBy(request.getCreditId());
        Product product = new Product(request.getAmount(),request.getDescription(),request.getShares());
        product.setDebt(request.getAmount());
        product.setInterest(credit.getInterest());
        credit.addProduct(product);
        Context.gateway.update(credit);
        new LedgerDebit().execute(new LedgerDebitRequest(request.getCreditId(),request.getAmount()));
    }
}

