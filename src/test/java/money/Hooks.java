package money;

import com.gallego.money.hex.model.entity.AppFunction;
import com.gallego.money.util.Context;
import com.gallego.money.util.TimeContext;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.util.date.DefaultTimeHandler;
import cucumber.api.java.Before;

import java.time.LocalDate;

public class Hooks {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
        AppFunction functionCreateCredit = new AppFunction("Create Credit");
        AppFunction functionPayCredit = new AppFunction("Pay Credit");
        Context.gateway.persist(functionCreateCredit);
        Context.gateway.persist(functionPayCredit);
        TimeContext.timeHandler = new DefaultTimeHandler(LocalDate.now());
    }
}
