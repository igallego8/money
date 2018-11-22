package money;

import com.gallego.money.model.*;
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

    @Then("^I see the next credit card charge for the month (\\d+)$")
    public void i_see_the_next_credit_card_charge_for_the_month(int month) throws Throwable {

    }


    @Then("^I see the next credit card charge for the month (\\d+) for \"([^\"]*)\" dollars$")
    public void i_see_the_next_credit_card_charge_for_the_month_for_dollars(int month, String amount) throws Throwable {
        Products products = Context.gateway.getProductsBy(creditId);
        PaymentService paymentService = new PaymentService();
        BigDecimal overAmount = BigDecimal.ZERO;
        for (int i = 0; i < month-1; i++) {
            overAmount=overAmount.add(paymentService.pay(creditId, products.getNextTotalCharge()));
        }
        BigDecimal nextCharge = products.getNextTotalCharge();
        Assert.assertEquals("Bad next charge",new BigDecimal(amount).setScale(2,BigDecimal.ROUND_HALF_UP),nextCharge.setScale(2,BigDecimal.ROUND_HALF_UP));

    }


}
