package money;

import com.gallego.money.boundery.NextPaymentChargeQuery;
import com.gallego.money.hex.model.credit.*;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditRequest;
import com.gallego.money.hex.model.credit.vo.PayCreditRequest;
import com.gallego.money.hex.model.credit.vo.PurchaseCreditRequest;
import com.gallego.money.hex.model.credit.vo.ReferCreditRequest;
import com.gallego.money.hex.model.entity.*;
import com.gallego.money.util.Context;
import com.gallego.money.util.TimeContext;
import com.gallego.money.util.date.IncreaseMonthHandler;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CreditCardSteps {

    private Long creditId;
    private Long old_creditId;
    private BigDecimal amount;

    @Given("^a buy of (\\d+) dollars$")
    public void a_buy_of_dollars(int amount) {
        Context.shoppingCart = new ShoppingCart();
        this.amount = BigDecimal.valueOf(amount);
    }

    @Given("^using a credit card with interest of \"([^\"]*)\"$")
    public void using_a_credit_card_with_interest_of(String interest){
        Credit credit = new Credit(new BigDecimal(100),new BigDecimal(0),Float.valueOf(interest) ,1);
        Context.gateway.persist(credit);
        creditId = credit.getId();
        Context.gateway.persist(new Ledger(credit.getId()));
    }

    @When("^I select number of share (\\d+)$")
    public void i_select_number_of_share(int shares) {
        CheckoutCredit checkoutCredit = new CheckoutCredit();
        Product product =new Product(amount, "Product Test",shares);
        Context.shoppingCart.add(product);
        Context.shoppingCart.getProducts().forEach(p->checkoutCredit.execute(new CheckoutCreditRequest(p.getAmount(),p.getDescription(),creditId,shares)));

    }

    @Then("^I see the next credit card charge for the month (\\d+) for \"([^\"]*)\" dollars$")
    public void i_see_the_next_credit_card_charge_for_the_month_for_dollars(int month, String amount){

        PayCredit payCredit = new PayCredit();
        BigDecimal overAmount = BigDecimal.ZERO;
        for (int i = 0; i < month-1; i++) {
            TimeContext.timeHandler.change(new IncreaseMonthHandler(), 1);
            overAmount = overAmount.add(payCredit.execute(new PayCreditRequest(creditId,new NextPaymentChargeQuery().query(creditId))));
        }
        TimeContext.timeHandler.change(new IncreaseMonthHandler(), 1);
        BigDecimal nextCharge = new NextPaymentChargeQuery().query(creditId);
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

        ShoppingCart cart = new ShoppingCart();
        Context.shoppingCart = cart;
        cart.add(new Product(new BigDecimal(prod1),"Product 1",0));
        cart.add(new Product(new BigDecimal(prod2),"Product 1",0));
        cart.add(new Product(new BigDecimal(prod3),"Product 1",0));
    }

    @Given("^using a credit card with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void using_a_credit_card_with_interest_of_and_number_of_share(String interest, int shares) {

        Credit credit = new Credit(new BigDecimal(10000),new BigDecimal(0),Float.valueOf(interest),30);
        Context.gateway.persist(credit);
        creditId = credit.getId();
        Context.gateway.persist(new Ledger(credit.getId()));

        Iterator<Product> it=Context.shoppingCart.getProducts().iterator();
        CheckoutCredit checkoutCredit = new CheckoutCredit();

        while(it.hasNext()){
            Product p = it.next();
            checkoutCredit.execute(new CheckoutCreditRequest(p.getAmount(),p.getDescription(),creditId,shares));
        }
    }

    @When("^pay credit for (\\d+) paid$")
    public void pay_credit_for_paid(int months){
        PayCredit payCredit = new PayCredit();
        for (int i = 0; i < months; i++) {
            TimeContext.timeHandler.change(new IncreaseMonthHandler(),1);
            payCredit.execute(new PayCreditRequest(creditId,new NextPaymentChargeQuery().query(creditId)));
        }
    }

    @Then("^I see the percentage of (\\d+) , (\\d+) and (\\d+)$")
    public void i_see_the_percentage_of_and(int prod1, int prod2, int prod3){
        Credit credit = Context.gateway.findCreditBy(creditId);
        List<Product> it=credit.getProducts();
        List<Integer> percentages = new ArrayList<>();
        for(Product p : it){
            percentages.add(p.percentagePaid());
        }
        Assert.assertEquals("Wrong percentage for product 1 ",prod1,percentages.get(0).intValue());
        Assert.assertEquals("Wrong percentage for product 2 ",prod2,percentages.get(1).intValue());
        Assert.assertEquals("Wrong percentage for product 3 ",prod3,percentages.get(2).intValue());
    }


    @When("^I defer the debt with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void i_defer_the_debt_with_interest_of_and_number_of_share(String interest, int shares) {
        ReferCredit referCredit = new ReferCredit();
        referCredit.execute(new ReferCreditRequest(creditId,shares,Float.valueOf(interest)));
    }



    @When("^an debt is purchased with interest of \"([^\"]*)\" and number of share (\\d+)$")
    public void an_debt_is_purchased_with_interest_of_and_number_of_share(String interest, int shares) {
        TotalCreditDebtQuery totalDebtQuery = new TotalCreditDebtQuery();
        PurchaseCredit purchaseCredit = new PurchaseCredit();
        Credit newCredit = purchaseCredit.execute(new PurchaseCreditRequest(creditId, totalDebtQuery.query(creditId), shares, Float.valueOf(interest), 15));
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
