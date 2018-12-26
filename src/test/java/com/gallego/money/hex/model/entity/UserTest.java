package com.gallego.money.entity;

import com.gallego.money.integration.MockGateway;
import com.gallego.money.util.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
    }


    @Test
    public void twoDifferentUsersAreNotSame(){
        User user1 = new User("User");
        User user2 = new User("User");
        Context.gateway.persist(user1);
        Context.gateway.persist(user2);

        Assert.assertFalse(user1.isSame(user2));

    }

    @Test
    public void oneUserIsSameToHimself(){
        User user = new User("User");
        Context.gateway.persist(user);
        Assert.assertTrue(user.isSame(user));
    }


    @Test
    public void oneUserWithOutIDIsNotSameToHimself(){
        User user = new User("User");
        Assert.assertFalse(user.isSame(user));
    }

    @Test
    public void twoDifferentUsersWithOutIdAreNotSame(){
        User user1 = new User("User");
        User user2 = new User("User");
        Assert.assertFalse(user1.isSame(user2));
    }
}
