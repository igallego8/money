package com.gallego.money.hex.model.refer.command;

import com.gallego.money.hex.model.checkout.command.CheckoutCustomInterestCredit;
import com.gallego.money.hex.model.checkout.vo.CheckoutCreditCustomInteresRequest;
import com.gallego.money.hex.model.payment.command.PayCredit;
import com.gallego.money.hex.model.payment.command.TotalToPayQuery;
import com.gallego.money.hex.model.payment.vo.PayCreditRequest;

import java.math.BigDecimal;

public class ReferCredit {

    public  void execute (ReferCreditRequest request){
        BigDecimal totalDebt = new TotalToPayQuery().query(request.getCreditId());
        new PayCredit().execute(new PayCreditRequest(request.getCreditId(),totalDebt));
        CheckoutCreditCustomInteresRequest r = new CheckoutCreditCustomInteresRequest(totalDebt,"Referred",request.getCreditId(),request.getShares(),request.getInterest());
        new CheckoutCustomInterestCredit().execute(r);
    }
}
