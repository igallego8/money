package com.gallego.money.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transac {

    private Long id;


    public Transac(BigDecimal total) {
        id = 1 + (long) (Math.random() * 10000);
    }




    public void setDate(Date date) {
    }

    public Long getId() {
        return id;
    }
}
