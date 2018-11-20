package com.gallego.money.model;

import java.math.BigDecimal;

public class MockGateway implements Gateway {

    Ledger ledger;
    @Override
    public CreditCard getCreditCard() {
        return new CreditCard(new BigDecimal(100), new BigDecimal(0));

    }

    @Override
    public Ledger getLedger(Integer id) {
        if (ledger == null)
            ledger = new Ledger(id);
        return ledger;
    }
}
