package com.gallego.money.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String invalid_argument) {
        super(invalid_argument);
    }
}
