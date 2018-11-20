package com.gallego.money.model;

import java.util.ArrayList;
import java.util.List;

public class Ledger {

    List<Transac> entries = new ArrayList<>();

    public Ledger(Integer id) {

    }

    public void registDebit(Transac transac) {
        transac.setType(TransacType.DEBIT);
        entries.add(transac);
    }
}
