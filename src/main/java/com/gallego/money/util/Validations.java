package com.gallego.money.util;

import com.gallego.money.exception.InvalidArgumentException;

public class Validations {
    public static void validateArgument(boolean b) {
        if(b)
            throw new InvalidArgumentException("Invalid argument");
    }
}
