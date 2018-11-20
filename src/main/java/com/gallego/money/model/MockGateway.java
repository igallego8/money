package com.gallego.money.model;

import java.math.BigDecimal;

public class MockGateway implements Gateway {
    @Override
    public CreditCard getCreditCard() {
        return new CreditCard(new BigDecimal(100), new BigDecimal(0));

    }
}
