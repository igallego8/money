package com.gallego.money.hex.support.security;

import com.gallego.money.hex.model.entity.AppFunction;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.License;
import com.gallego.money.hex.model.entity.User;

import java.util.List;

public class UserService {

    public  boolean isLicensedToViewFunction(User user, AppFunction function) {
        List<License> licenses= Context.gateway.findLicensesByUserAndFunction(user, function);
        return !licenses.isEmpty();
    }
}
