package com.gallego.money.hex.model.credit;

import com.gallego.money.hex.Command;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditCustomInteresRequest;
import com.gallego.money.hex.model.credit.vo.PayCreditRequest;
import com.gallego.money.hex.model.credit.vo.ReferCreditRequest;

import java.math.BigDecimal;

@Command
public class ReferCredit {

    public  void execute (ReferCreditRequest request){
        BigDecimal totalDebt = new TotalToPayQuery().query(request.getCreditId());
        new PayCredit().execute(new PayCreditRequest(request.getCreditId(),totalDebt));
        CheckoutCreditCustomInteresRequest r = new CheckoutCreditCustomInteresRequest(totalDebt,"Referred",request.getCreditId(),request.getShares(),request.getInterest());
        new CheckoutCustomInterestCredit().execute(r);
    }
}
