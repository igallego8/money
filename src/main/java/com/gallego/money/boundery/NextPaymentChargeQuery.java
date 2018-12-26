package com.gallego.money.boundery;

import com.gallego.money.hex.Query;
import com.gallego.money.util.Context;

import java.math.BigDecimal;

@Query
public class NextPaymentChargeQuery {


    public BigDecimal query(Long creditId) {
        return Context.gateway.findCreditBy(creditId).getNextTotalCharge();
    }

}
