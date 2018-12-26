package com.gallego.money.boundery;

import com.gallego.money.hex.model.entity.Product;
import com.gallego.money.util.Context;

import java.util.List;


public class CreditProductSearchService {

    public List<Product> search(Long creditId) {
        return Context.gateway.findCreditBy(creditId).getProducts();
    }
}
