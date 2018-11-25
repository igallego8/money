package com.gallego.money.checkout;

import com.gallego.money.entity.Products;

import java.math.BigDecimal;

public abstract class  CheckoutProcess {

  protected abstract void process(Products products, Long assetId, BigDecimal amount);
}
