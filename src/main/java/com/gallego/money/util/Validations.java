package com.gallego.money.hex.model.entity;

public class Validations {
    public static void validateArgument(boolean b) {
        if(b)
            throw new InvalidArgumentException("Invalid argument");
    }
}
