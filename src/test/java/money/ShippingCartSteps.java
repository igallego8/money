package money;


import com.gallego.money.entity.Context;
import com.gallego.money.entity.Product;
import com.gallego.money.entity.ShoppingCart;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.math.BigDecimal;


public class ShippingCartSteps {

    @Given("^a shipping cart$")
    public void a_shipping_cart() {
        Context.shoppingCart = new ShoppingCart();
    }

    @When("^I add product to shipping card$")
    public void i_add_product_to_shipping_card() throws Throwable {
        for (int x = 0; x < 10;  x++) {
            Context.shoppingCart.add(new Product(new BigDecimal(40)));
        }
    }

    @Then("^I see the the total amount$")
    public void i_see_the_the_total_amount() throws Throwable {
        Assert.assertThat("", Context.shoppingCart.getTotal(), Matchers.is(new BigDecimal(40*10)));
    }
}
