package com.gallego.money.hex.model.payment.command;

import com.gallego.money.entity.Credit;
import com.gallego.money.hex.model.checkout.command.Command;
import com.gallego.money.hex.model.ledger.command.LedgerCredit;
import com.gallego.money.hex.model.ledger.vo.LedgerCreditRequest;
import com.gallego.money.hex.model.payment.vo.PayCreditRequest;
import com.gallego.money.util.Context;

import java.math.BigDecimal;

@Command
public class PayCredit {

    public BigDecimal execute(PayCreditRequest request){
        Credit credit = Context.gateway.findCreditBy(request.getCreditId());
        BigDecimal leftAmount= credit.credit(request.getAmount());
        Context.gateway.update(credit);
        // PayCredit PAID
        new LedgerCredit().execute(new LedgerCreditRequest(request.getCreditId(),request.getAmount()));
        return leftAmount;
    }
}
