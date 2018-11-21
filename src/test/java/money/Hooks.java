package money;

import com.gallego.money.model.Context;
import com.gallego.money.model.MockGateway;
import cucumber.api.java.Before;

public class Hooks {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
    }
}
