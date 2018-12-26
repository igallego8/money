package com.gallego.money.security;

import com.gallego.money.entity.AppFunction;
import com.gallego.money.util.Context;
import com.gallego.money.entity.License;
import com.gallego.money.entity.User;

import java.util.List;

public class UserService {

    public  boolean isLicensedToViewFunction(User user, AppFunction function) {
        List<License> licenses= Context.gateway.findLicensesByUserAndFunction(user, function);
        return !licenses.isEmpty();
    }
}
