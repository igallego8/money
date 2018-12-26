package com.gallego.money.util.date;

import com.gallego.money.util.date.DateChangeHandler;

import java.time.LocalDate;

public class IncreaseMonthHandler implements DateChangeHandler {
    @Override
    public LocalDate process(LocalDate date, int offset) {
        return date.plusMonths(offset);
    }
}
