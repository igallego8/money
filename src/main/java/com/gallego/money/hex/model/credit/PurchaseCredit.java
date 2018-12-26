package com.gallego.money.hex.model.purchase.command;

import com.gallego.money.entity.Credit;
import com.gallego.money.hex.model.checkout.command.CheckoutCredit;
import com.gallego.money.hex.model.checkout.command.Command;
import com.gallego.money.hex.model.checkout.vo.CheckoutCreditRequest;
import com.gallego.money.hex.model.payment.command.PayCredit;
import com.gallego.money.hex.model.payment.vo.PayCreditRequest;
import com.gallego.money.util.Context;

@Command
public class PurchaseCredit {

    public Credit execute(PurchaseCreditRequest request){
        new PayCredit().execute(new PayCreditRequest(request.getCreditId(), request.getAmount()));
        Credit credit = new Credit(request.getAmount(), request.getAmount(),request.getInterest(),request.getCutoffDay());
        Context.gateway.persist(credit);
        new CheckoutCredit().execute(new CheckoutCreditRequest(request.getAmount(),"Debt purchased",credit.getId(),request.getShares()));
        return credit;

    }
}
