package money;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerCreatteNeed {


    @Given("^buy of products for amount$")
    public void buy_of_products_for_amount() throws Throwable {
        UserL u = new UserL();

    }

    @When("^an debt is purchased with interest$")
    public void an_debt_is_purchased_with_interest() throws Throwable {
        NeedService.create(new Nedd());
    }

    @Then("^I see new credit with debt$")
    public void i_see_new_credit_with_debt() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }
}
