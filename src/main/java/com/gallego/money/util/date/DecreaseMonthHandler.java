package com.gallego.money;

import com.gallego.money.entity.DateChangeHandler;

import java.time.LocalDate;

public class DecreaseMonthHandler implements DateChangeHandler {
    @Override
    public LocalDate process(LocalDate date, int offset) {
        return date.minusMonths(offset);
    }
}
