package com.gallego.money.hex.model.credit;

import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.hex.Query;
import com.gallego.money.util.Context;

import java.math.BigDecimal;

@Query
public class TotalToPayQuery {

    public BigDecimal query(Long creditId){
        Credit credit = Context.gateway.findCreditBy(creditId);
        return credit.getTotalDebt();
    }
}
