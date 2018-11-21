package com.gallego.money.model;

import java.util.Iterator;
import java.util.List;

public class ProductIterator implements Iterator<Product> {
    private List<Product> products;
    private int index = 0;

    public ProductIterator (List<Product> products){
        this.products = products;
    }

    @Override
    public boolean hasNext() {
        return index < products.size();
    }

    @Override
    public Product next() {
        return products.get(index++);
    }
}
