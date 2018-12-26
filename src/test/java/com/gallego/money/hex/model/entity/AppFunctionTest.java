package com.gallego.money.hex.model.entity;

import org.junit.Assert;
import org.junit.Test;

public class AppFunctionTest {


    @Test
    public void twoDifferentFunctionAreNotSame(){
        AppFunction function1 = new AppFunction("A");
        AppFunction function2 = new AppFunction("B");
        Assert.assertFalse(function1.isSame(function2));

    }

    @Test
    public void oneFunctionIsSameToItself(){
        AppFunction function1 = new AppFunction("A");
        Assert.assertTrue(function1.isSame(function1));
    }
}
