package com.gallego.money;

import com.gallego.money.model.CreditCard;
import com.gallego.money.model.CreditProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


public class CreditProcessorTest {

    CreditProcessor creditProcessor = new CreditProcessor(new CreditCard(new BigDecimal(100), new BigDecimal(0)));

    @Test
    public void test (){

        Assert.assertEquals("Wrong value for next payment", creditProcessor.nextPayment() ,new BigDecimal(40.00));
    }
}
