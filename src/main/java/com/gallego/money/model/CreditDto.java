package com.gallego.money.model;

import java.math.BigDecimal;

public class CreditDto {

    public BigDecimal nextPayment;
    public BigDecimal debt;
    public float interest;
    public Long id;
    public Integer cutoffDay;
}
