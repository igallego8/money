package com.gallego.money.entity;

import java.time.LocalDate;

public interface DateChangeHandler {

    LocalDate process(LocalDate date, int offset);
}
