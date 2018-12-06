package money;

import com.gallego.money.entity.AppFunction;
import com.gallego.money.entity.Context;
import com.gallego.money.integration.MockGateway;
import cucumber.api.java.Before;

public class Hooks {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
        AppFunction functionCreateCredit = new AppFunction("Create Credit");
        AppFunction functionPayCredit = new AppFunction("Pay Credit");
        Context.gateway.persist(functionCreateCredit);
        Context.gateway.persist(functionPayCredit);

    }
}
