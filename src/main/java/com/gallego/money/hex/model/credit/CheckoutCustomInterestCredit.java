package com.gallego.money.hex.model.credit;

import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.hex.model.entity.Product;
import com.gallego.money.hex.Command;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditCustomInteresRequest;
import com.gallego.money.hex.model.ledger.command.LedgerDebit;
import com.gallego.money.hex.model.ledger.vo.LedgerDebitRequest;
import com.gallego.money.util.Context;

@Command
public class CheckoutCustomInterestCredit {

    public void execute(CheckoutCreditCustomInteresRequest request) {
        Credit credit = Context.gateway.findCreditBy(request.getCreditId());
        Product product = new Product(request.getAmount(),request.getDescription(), request.getShares());
        product.setInterest(request.getInterest());
        product.setDebt(request.getAmount());
        credit.addProduct(product);
        Context.gateway.update(credit);
        new LedgerDebit().execute(new LedgerDebitRequest(request.getCreditId(),request.getAmount()));
    }

}

