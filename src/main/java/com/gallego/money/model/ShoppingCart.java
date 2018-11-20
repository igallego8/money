package com.gallego.money.model;

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
}
