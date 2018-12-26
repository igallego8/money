package com.gallego.money;


import com.gallego.money.boundery.NextPaymentChargeQuery;
import com.gallego.money.hex.model.credit.ReferCredit;
import com.gallego.money.hex.model.credit.vo.ReferCreditRequest;
import com.gallego.money.hex.model.entity.*;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.model.CreditReferRequest;
import com.gallego.money.util.Context;
import com.gallego.money.util.date.DefaultTimeHandler;
import com.gallego.money.util.date.IncreaseMonthHandler;
import com.gallego.money.util.TimeContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReferServiceTest {

    @Before
    public void init(){

        Context.gateway = new MockGateway();
        TimeContext.timeHandler = new DefaultTimeHandler(LocalDate.now());
    }

    @Test
    public void test(){

        Credit credit = new Credit(BigDecimal.valueOf(1000),BigDecimal.ZERO, 2.1f,10);
        Context.gateway.persist(credit);
        Product p = new Product(BigDecimal.TEN,"Product Test",24);
        p.setCreditId(credit.getId());
        p.setDebt(BigDecimal.TEN);
        p.setInterest(2.1f);
        credit.addProduct(p);
        Context.gateway.persist(credit);

        ReferCredit referCredit = new  ReferCredit();
        CreditReferRequest referRequest = new CreditReferRequest();
        referRequest.creditId = credit.getId();
        referRequest.interest = 1f;
        referRequest.shares = 10;
        referCredit.execute(new ReferCreditRequest(credit.getId(),10,1f));
        TimeContext.timeHandler.change(new IncreaseMonthHandler(),3);
        Assert.assertEquals("Wrong product next charge" + new  NextPaymentChargeQuery().query(credit.getId()).toString(),0,new  NextPaymentChargeQuery().query(credit.getId()).compareTo(BigDecimal.valueOf(1.1)));

    }
}
