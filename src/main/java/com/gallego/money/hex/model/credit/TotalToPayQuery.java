package com.gallego.money.hex.model.payment.command;

import com.gallego.money.entity.Credit;
import com.gallego.money.payment.ProductCreditHandler;
import com.gallego.money.util.Context;

import java.math.BigDecimal;

@Query
public class TotalToPayQuery {

    public BigDecimal query(Long creditId){
        Credit credit = Context.gateway.findCreditBy(creditId);
        return credit.getTotalDebt();
    }
}
