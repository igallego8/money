package com.gallego.money.boundery;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Products;


public class CreditProductSearchService {
    public Products search(Long creditId) {
        return Context.gateway.getProductsBy(creditId);
    }
}
