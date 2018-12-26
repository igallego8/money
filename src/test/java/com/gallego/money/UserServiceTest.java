package com.gallego.money;

import com.gallego.money.hex.model.entity.AppFunction;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.License;
import com.gallego.money.hex.model.entity.User;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.hex.support.security.UserService;
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
