package com.gallego.money.payment;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Ledger;
import com.gallego.money.entity.Products;
import com.gallego.money.integration.RegistryNotFoundException;

import java.math.BigDecimal;


public class PaymentService {

    public BigDecimal pay(Long creditId, BigDecimal amount) {

        Products products = Context.gateway.getProductsBy(creditId);
        BigDecimal leftAmount= products.credit(amount);
        Context.gateway.update(products);

        Ledger ledger;
        try {
             ledger = Context.gateway.getLedger(creditId);
        }catch (RegistryNotFoundException ex){
             ledger = new Ledger(creditId);
             Context.gateway.persist(ledger);
        }
        ledger.credit(amount);

        return leftAmount;
    }
}
