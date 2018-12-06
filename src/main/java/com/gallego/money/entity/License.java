package com.gallego.money.entity;

public class License {

    private User user;
    private AppFunction function;

    public License(User user, AppFunction function) {
        this.user = user;
        this.function = function;
    }

    public User getUser() {
        return user;
    }

    public AppFunction getFunction() {
        return function;
    }
}
