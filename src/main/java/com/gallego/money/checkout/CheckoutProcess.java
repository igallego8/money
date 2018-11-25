package com.gallego.money.model;

import java.math.BigDecimal;

public interface CheckoutProcess {

    void process(Products products, Long assetId, BigDecimal amount);
}
