package com.gallego.money.hex.model.ledger.command;

import com.gallego.money.hex.model.entity.Ledger;
import com.gallego.money.hex.model.ledger.vo.LedgerCreditRequest;
import com.gallego.money.exception.RegistryNotFoundException;
import com.gallego.money.util.Context;

public class LedgerCredit {

    public  void execute(LedgerCreditRequest request){
        Ledger ledger;
        try {
            ledger = Context.gateway.getLedger(request.getCreditId());
        }catch (RegistryNotFoundException ex){
            ledger = new Ledger(request.getCreditId());
            Context.gateway.persist(ledger);
        }
        ledger.credit(request.getAmount());

    }
}
