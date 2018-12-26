package money;


import com.gallego.money.entity.DateChangeHandler;

import java.time.LocalDate;

public interface TimeHandler {
    void change(DateChangeHandler handler, int offset);

    LocalDate today();
}
