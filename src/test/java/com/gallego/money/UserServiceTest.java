package com.gallego.money;

import com.gallego.money.entity.AppFunction;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.License;
import com.gallego.money.entity.User;
import com.gallego.money.integration.MockGateway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

    UserService userService;
    User user;
    AppFunction createCreditFunction = new AppFunction("Create Credit");

    @Before
    public void init(){
        userService = new UserService();
        Context.gateway = new MockGateway();
        user = new User("Test");
        Context.gateway.persist(user);
        License license = new License(user, createCreditFunction);
        Context.gateway.persist(license);
    }

    @Test
    public void givenUserLicenseToCreateCredit_HeCanSeeCreateCreditFunction() {
        Assert.assertTrue(userService.isLicensedToViewFunction(user, createCreditFunction));
    }


    @Test
    public void givenUserWithoutLicenseToCreateCredit_CanNotSeeCreateCreditFunction() {
        user = new User("User");
        Context.gateway.persist(user);
        Assert.assertFalse(userService.isLicensedToViewFunction(user, createCreditFunction));
    }

}
