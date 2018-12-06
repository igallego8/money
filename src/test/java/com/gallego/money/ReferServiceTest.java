package com.gallego.money;


import com.gallego.money.checkout.ReferService;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.Credit;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.model.CreditReferRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ReferServiceTest {

    @Before
    public void init(){

        Context.gateway = new MockGateway();

    }

    @Test
    public void test(){

        Credit credit = new Credit(BigDecimal.valueOf(1000), 2.1f);
        Context.gateway.persist(credit);

        Product p = new Product(BigDecimal.TEN,"Product Test",24);
        p.setCreditId(credit.getId());
        p.setDebt(BigDecimal.TEN);
        p.setInterest(2.1f);
        Context.gateway.persist(new Products(Arrays.asList(p)));
        CreditReferRequest referRequest = new CreditReferRequest();
        referRequest.creditId = credit.getId();
        referRequest.interest = 1f;
        referRequest.shares = 10;

        ReferService service = new ReferService();
        service.defer(referRequest);
        Products products = Context.gateway.getProductsBy(credit.getId());
        Assert.assertEquals("Wrong product next charge",0,products.getNextTotalCharge().compareTo(BigDecimal.valueOf(1.1)));

    }
}
