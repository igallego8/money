package money;

import com.gallego.money.model.*;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;

public class CreditCardSteps {


    @Before
    public void init(){
        Context.gateway = new MockGateway();
        Context.shoppingCart = new ShoppingCart();
    }

    @Given("^a buy of (\\d+) dollars$")
    public void a_buy_of_dollars(int arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Product product =new Product(new BigDecimal(40.00));
        Context.shoppingCart.add(product);
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.pay();


    }

    @When("^I select number of share (\\d+)$")
    public void i_select_number_of_share(int arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CreditCard creditCard = Context.gateway.getCreditCard();
        CreditCardPayment payment = new CreditCardPayment(creditCard);
        payment.pay(Context.shoppingCart.getProducts(), 4);

    }

    @Then("^I see the next credit card bill amount$")
    public void i_see_the_next_credit_card_bill_amount() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CreditCard creditCard = Context.gateway.getCreditCard();
        CreditProcessor creditProcessor = new CreditProcessor(creditCard);
        creditProcessor.nextPayment();
    }


}
