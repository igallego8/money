package money;

import com.gallego.money.entity.DateChangeHandler;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DefaultTimeHandler implements TimeHandler {

    private LocalDate date;

    public  DefaultTimeHandler( LocalDate date){
        this.date = date;
    }
    @Override
    public void change(DateChangeHandler handler, int offset) {
       date = handler.process(date , offset);
    }

    @Override
    public LocalDate today() {
        return date;
    }


}
