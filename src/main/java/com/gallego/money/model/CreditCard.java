package com.gallego.money.model;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal quota;
    private BigDecimal debt;


    public CreditCard(BigDecimal quota, BigDecimal debt) {
        this.quota = quota;
        this.debt = debt;
    }
}
