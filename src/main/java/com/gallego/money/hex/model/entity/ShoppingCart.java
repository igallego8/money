package com.gallego.money.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }


    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getTotal() {
        return products.stream().map(p -> p.getAmount()).reduce(new BigDecimal(0), (x, y) -> x.add(y));
    }

}
