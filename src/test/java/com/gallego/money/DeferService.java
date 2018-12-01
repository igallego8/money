package com.gallego.money;

import com.gallego.money.entity.Context;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.Products;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeferService {
    public void defer(Long creditId, Float interest, int shares) {
        Products products = Context.gateway.getProductsBy(creditId);
        BigDecimal totalDebt = products.getTotalDebt();
        Product product = new Product(totalDebt, "Deferred",shares);
        product.setDebt(totalDebt);
        product.setCreditId(creditId);
        product.setInterest(interest);
        Context.gateway.delete(products);
        List<Product> products1 = new ArrayList<>();
        products1.add(product);
        products = new Products(products1);
        Context.gateway.persist(products);
    }
}
