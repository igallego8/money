package money;

import com.gallego.money.model.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


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

    @Then("^I see the next credit card charge for the month (\\d+) for \"([^\"]*)\" dollars$")
    public void i_see_the_next_credit_card_charge_for_the_month_for_dollars(int month, String amount){
        Products products = Context.gateway.getProductsBy(creditId);
        PaymentService paymentService = new PaymentService();
        BigDecimal overAmount = BigDecimal.ZERO;
        for (int i = 0; i < month-1; i++) {
            overAmount = overAmount.add(paymentService.pay(creditId, products.getNextTotalCharge()));
        }
        BigDecimal nextCharge = products.getNextTotalCharge();
        Assert.assertEquals("Bad next charge",new BigDecimal(amount).setScale(2,BigDecimal.ROUND_HALF_UP),nextCharge.setScale(2,BigDecimal.ROUND_HALF_UP));
    }

    @Then("^the ledger must have debits for \"([^\"]*)\" and Credits for \"([^\"]*)\"$")
    public void the_ledger_must_have_debits_for_and_Credits_for(String debits, String credits){
        Ledger ledger = Context.gateway.getLedger(creditId);
        Assert.assertEquals("Wrong value for Debits",new BigDecimal(debits).setScale(2,BigDecimal.ROUND_HALF_UP),ledger.getTotalDebits().setScale(2,BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals("Wrong value for Credits",new BigDecimal(credits).setScale(2,BigDecimal.ROUND_HALF_UP),ledger.getTotalCredits().setScale(2,BigDecimal.ROUND_HALF_UP));
    }

    @Given("^buy of products for amount of \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\"$")
    public void buy_of_products_for_amount_of_and(String prod1, String prod2, String prod3){
        CreditCard creditCard = new CreditCard(new BigDecimal(10000),new BigDecimal(0));
        creditId = creditCard.getId();
        Context.gateway.persist(creditCard);
        ShoppingCart cart = new ShoppingCart();
        Context.shoppingCart = cart;
        cart.add(new Product(new BigDecimal(prod1)));
        cart.add(new Product(new BigDecimal(prod2)));
        cart.add(new Product(new BigDecimal(prod3)));
    }



    @Given("^using a credit card with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void using_a_credit_card_with_interest_of_and_number_of_share(String interest, int shares) throws Throwable {
        Iterator<Product> it=Context.shoppingCart.getProducts().iterator();
        CheckoutService checkoutService= new CheckoutService();
        while(it.hasNext()){
            Product p = it.next();
            p.setCreditId(creditId);
            p.setInterest(Float.valueOf(interest));
            checkoutService.payWithCredit(new Products(Arrays.asList(p)),creditId,shares,p.getAmount());
        }
    }


    @When("^pay credit for (\\d+) paid$")
    public void pay_credit_for_paid(int months){
        Products products = Context.gateway.getProductsBy(creditId);
        PaymentService paymentService = new PaymentService();
        for (int i = 0; i < months-1; i++) {
            paymentService.pay(creditId, products.getNextTotalCharge());
        }
    }

    @Then("^I see the percentage of (\\d+) , (\\d+) and (\\d+)$")
    public void i_see_the_percentage_of_and(int prod1, int prod2, int prod3){
        Products products = Context.gateway.getProductsBy(creditId);
        Iterator<Product> it=products.iterator();
        List<Integer> percentages = new ArrayList<>();
        while(it.hasNext()){
            percentages.add(it.next().percentagePaid());
        }
        Assert.assertEquals("Wrong percentage for product 1 ",prod1,percentages.get(0).intValue());
        Assert.assertEquals("Wrong percentage for product 2 ",prod2,percentages.get(1).intValue());
        Assert.assertEquals("Wrong percentage for product 3 ",prod3,percentages.get(2).intValue());
    }

}
