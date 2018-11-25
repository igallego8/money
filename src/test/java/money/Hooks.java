package money;

import com.gallego.money.entity.Context;
import com.gallego.money.integration.MockGateway;
import cucumber.api.java.Before;

public class Hooks {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
    }
}
