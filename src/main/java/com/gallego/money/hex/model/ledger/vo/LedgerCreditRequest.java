package com.gallego.money.hex.model.ledger.command;

import java.math.BigDecimal;

public class LedgerCreditRequest {
    private Long creditId;
    private BigDecimal amount;

    public LedgerCreditRequest(Long creditId, BigDecimal amount) {
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
