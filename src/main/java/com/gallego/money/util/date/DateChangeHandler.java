package com.gallego.money.util.date;

import java.time.LocalDate;

public interface DateChangeHandler {

    LocalDate process(LocalDate date, int offset);
}
