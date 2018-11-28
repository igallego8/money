package com.gallego.money.model;

import java.math.BigDecimal;

public class ProductDto {

    public BigDecimal amount;
    public BigDecimal debt;
    public String description;
    public Long creditId;
    public Integer shares;
    public Float interest;

    public ProductDto(BigDecimal amount) {
        this.amount = amount;
    }

    public ProductDto() {
        this.amount = BigDecimal.ZERO;
    }
}
