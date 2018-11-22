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


public class CashSteps {

    private Long bankId=0l;


    @Given("^with amount of \"([^\"]*)\" in bank$")
    public void with_amount_of_in_bank(String amount) {
        Ledger ledger = new Ledger(bankId);
        ledger.credit(new BigDecimal(amount));
        Context.gateway.persist(ledger);
    }

    @When("^I buy  products amount of \"([^\"]*)\" and \"([^\"]*)\" paid with cash$")
    public void i_buy_a_product_amount_of_paid_with_cash(String amount1,String amount2 ) {
        Product product1 = new Product(new BigDecimal(amount1));
        Product product2 = new Product(new BigDecimal(amount2));
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.payWithCash(new Products(Arrays.asList(product1,product2)),bankId);
    }

    @Then("^I see the cash ledger with debits for \"([^\"]*)\" and  credits for\"([^\"]*)\"$")
    public void i_see_the_cash_ledger_with_debits_for_and_credits_for(String debits_amount, String credits_amount) {
        Ledger ledger = Context.gateway.getLedger(bankId);
        Assert.assertEquals("Wrong value for ledger Credits",ledger.getTotalCredits().setScale(2,BigDecimal.ROUND_HALF_UP),new BigDecimal(credits_amount).setScale(2,BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals("Wrong value for ledger Debits",ledger.getTotalDebits().setScale(2,BigDecimal.ROUND_HALF_UP),new BigDecimal(debits_amount).setScale(2,BigDecimal.ROUND_HALF_UP));
    }

}
