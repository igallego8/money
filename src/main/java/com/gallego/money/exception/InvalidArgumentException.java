package com.gallego.money.hex.model.entity;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String invalid_argument) {
        super(invalid_argument);
    }
}
