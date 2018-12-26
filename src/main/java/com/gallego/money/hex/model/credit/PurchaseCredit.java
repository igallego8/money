package com.gallego.money.hex.model.credit;

import com.gallego.money.hex.model.credit.vo.PayCreditRequest;
import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.hex.Command;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditRequest;
import com.gallego.money.hex.model.credit.vo.PurchaseCreditRequest;
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
