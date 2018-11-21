package com.gallego.money.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private List<BigDecimal> entries = new ArrayList<>();

    private Long assestId;

    public Ledger(Long assestId) {
        this.assestId = assestId;

    }

    public Long getAssestId() {
        return assestId;
    }

    public void debit(BigDecimal amount) {
        entries.add(amount.negate());
    }
}
