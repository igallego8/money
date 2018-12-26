package com.gallego.money.util.date;


import com.gallego.money.util.date.DateChangeHandler;

import java.time.LocalDate;
import java.util.concurrent.Flow;

public interface TimeHandler extends Flow.Publisher  {

    void change(DateChangeHandler handler, int offset);

    LocalDate today();

    void setDate(LocalDate localDate);
}
