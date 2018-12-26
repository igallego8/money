package com.gallego.money.hex.model.credit.vo;

import com.gallego.money.hex.Command;

import java.math.BigDecimal;

@Command
public class PayCreditRequest {
    private Long creditId;
    private BigDecimal amount;

    public PayCreditRequest(Long creditId, BigDecimal amount) {
        this.creditId = creditId;
        this.amount = amount;
    }

    public Long getCreditId() {
        return creditId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
