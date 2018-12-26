package com.gallego.money.hex.model.entity;

public class User extends Entity {
    private String name;

    public User(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
