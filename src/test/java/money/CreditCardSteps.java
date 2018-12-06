package money;

import com.gallego.money.PurchaseCreditService;
import com.gallego.money.checkout.ReferService;
import com.gallego.money.checkout.CheckoutService;
import com.gallego.money.checkout.DefaultCheckoutProcess;
import com.gallego.money.entity.*;
import com.gallego.money.model.BuyCreditRequest;
import com.gallego.money.model.BuyProductsCreditRequest;
import com.gallego.money.model.CreditReferRequest;
import com.gallego.money.payment.PaymentService;
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
    private Long old_creditId;


    @Given("^a buy of (\\d+) dollars$")
    public void a_buy_of_dollars(int amount) {
        Context.shoppingCart = new ShoppingCart();
        Product product =new Product(new BigDecimal(amount));
        Context.shoppingCart.add(product);
    }

    @Given("^using a credit card with interest of \"([^\"]*)\"$")
    public void using_a_credit_card_with_interest_of(String interest){
        Credit credit = new Credit(new BigDecimal(100),new BigDecimal(0));
        creditId = credit.getId();
        credit.setInterest(Float.valueOf(interest));
        Context.gateway.persist(new Ledger(credit.getId()));
        Context.gateway.persist(credit);
    }

    @When("^I select number of share (\\d+)$")
    public void i_select_number_of_share(int shares) {
        CheckoutService checkoutService = createCheckoutService();
        for (Product p : Context.shoppingCart.getProducts()){
            BuyCreditRequest request =  new BuyCreditRequest();
            request.creditId = creditId;
            request.shares = shares;
            request.amount = p.getAmount();
            request.description = p.getDescription();
            checkoutService.payWithCredit(request);
        }
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
        Credit credit = new Credit(new BigDecimal(10000),new BigDecimal(0));
        creditId = credit.getId();
        Context.gateway.persist(new Ledger(credit.getId()));
        Context.gateway.persist(credit);
        ShoppingCart cart = new ShoppingCart();
        Context.shoppingCart = cart;
        cart.add(new Product(new BigDecimal(prod1)));
        cart.add(new Product(new BigDecimal(prod2)));
        cart.add(new Product(new BigDecimal(prod3)));
    }

    @Given("^using a credit card with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void using_a_credit_card_with_interest_of_and_number_of_share(String interest, int shares) {
        Iterator<Product> it=Context.shoppingCart.getProducts().iterator();
        CheckoutService checkoutService = createCheckoutService();

        while(it.hasNext()){
            BuyCreditRequest request = new BuyCreditRequest();
            Product p = it.next();
            request.amount = p.getAmount();
            request.shares = shares;
            request.description = p.getDescription();
            request.creditId = creditId;
            checkoutService.payWithCredit(request);
        }
    }

    private CheckoutService createCheckoutService() {
        return new CheckoutService(new DefaultCheckoutProcess());
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


    @When("^I defer the debt with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void i_defer_the_debt_with_interest_of_and_number_of_share(String interest, int shares) throws Throwable {
        ReferService referService = new ReferService();
        CreditReferRequest referRequest = new CreditReferRequest();
        referRequest.creditId = creditId;
        referRequest.shares = shares;
        referRequest.interest = Float.valueOf(interest);
        referService.defer(referRequest);
    }



    @When("^an debt is purchased with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void an_debt_is_purchased_with_interest_of_and_number_of_share(String interest, int shares) {
        Products products = Context.gateway.getProductsBy(creditId);
        PurchaseCreditService purchaseCreditService = new PurchaseCreditService();
        Credit newCredit =purchaseCreditService.purchase(creditId, products.getTotalDebt(), shares, Float.valueOf(interest));
        old_creditId = creditId;
        creditId = newCredit.getId();

    }

    @Then("^I see new credit with debt of \"([^\"]*)\" and shares (\\d+)$")
    public void i_see_new_credit_with_debt_of_and_shares(String amount, int shares)  {
        List<Credit> credits= Context.gateway.fetchCredits();
        Assert.assertEquals("",1,credits.stream().filter(c-> c.getDebt().compareTo(new BigDecimal(amount))==0).count());



    }

    @Then("^the old ledger must have credit for \"([^\"]*)\" and new debit for \"([^\"]*)\"$")
    public void the_old_ledger_must_have_credit_for_and_new_credit_for(String amount_credit, String amount_debt) {
        Ledger ledger = Context.gateway.getLedger(creditId);
        Ledger old_credit_ledger = Context.gateway.getLedger(old_creditId);
        Assert.assertTrue("",old_credit_ledger.getTotalCredits().compareTo(new BigDecimal(amount_credit))==0);
        Assert.assertTrue("",ledger.getTotalDebits().compareTo(new BigDecimal(amount_debt).multiply(BigDecimal.valueOf(-1)))==0);


    }

}
