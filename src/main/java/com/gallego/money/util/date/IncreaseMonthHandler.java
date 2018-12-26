package com.gallego.money.entity;

import java.time.LocalDate;

public class IncreaseMonthHandler implements  DateChangeHandler {
    @Override
    public LocalDate process(LocalDate date, int offset) {
        return date.plusMonths(offset);
    }
}
