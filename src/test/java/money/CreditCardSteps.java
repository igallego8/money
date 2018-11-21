package money;

import com.gallego.money.model.*;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;


public class CreditCardSteps {


    private Long creditId;

    @Given("^a buy of (\\d+) dollars$")
    public void a_buy_of_dollars(int amount) {
        Context.shoppingCart = new ShoppingCart();
        Product product =new Product(new BigDecimal(amount));
        Context.shoppingCart.add(product);
    }

    @Given("^using a credit card with interest of \"([^\"]*)\"$")
    public void using_a_credit_card_with_interest_of(String interest){
        CreditCard creditCard = new CreditCard(new BigDecimal(100),new BigDecimal(0));
        creditId = creditCard.getId();
        creditCard.setInterest(Float.valueOf(interest));
        Context.gateway.persist(new Ledger(creditCard.getId()));
        Context.gateway.persist(creditCard);
    }


    @When("^I select number of share (\\d+)$")
    public void i_select_number_of_share(int shares) {
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.payWithCredit(new Products(Context.shoppingCart.getProducts()),creditId, shares,  Context.shoppingCart.getTotal().subtract(new BigDecimal(0)));
    }

    @Then("^I see the next credit card bill amount$")
    public void i_see_the_next_credit_card_bill_amount() {
        Products products = Context.gateway.getProductsBy(creditId);
        BigDecimal nextCharge = products.getNextCharge();
        Assert.assertEquals("Bad next charge",nextCharge.setScale(2,BigDecimal.ROUND_HALF_UP),new BigDecimal(13).setScale(2,BigDecimal.ROUND_HALF_UP));

    }
}
