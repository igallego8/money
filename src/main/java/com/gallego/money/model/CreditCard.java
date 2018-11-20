package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.Random;

public class CreditCard {
    private BigDecimal quota;
    private BigDecimal debt;

    private Integer id;

    public CreditCard(BigDecimal quota, BigDecimal debt) {
        this.quota = quota;
        this.debt = debt;
        id = 0;
    }

    public Integer getId() {
        return id;
    }
}
