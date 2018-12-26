package money;

import com.gallego.money.hex.model.credit.CheckoutCredit;
import com.gallego.money.hex.model.credit.vo.CheckoutCreditRequest;
import com.gallego.money.util.Context;
import com.gallego.money.hex.model.entity.Credit;
import com.gallego.money.util.date.IncreaseMonthHandler;
import com.gallego.money.util.TimeContext;
import com.gallego.money.integration.MockGateway;
import com.gallego.money.util.date.DefaultTimeHandler;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

public class InterestSteps {

    Long creditId;

    @Before
    public void init(){
        Context.gateway = new MockGateway();
        TimeContext.timeHandler = new DefaultTimeHandler(LocalDate.now());
    }

    @Given("^a credit card with interest of \"([^\"]*)\" and cutoff day (\\d+)$")
    public void a_credit_card_with_interest_of_and_date(String interest, int cutoffDay) throws ParseException {
        Credit credit = new Credit(BigDecimal.valueOf(1000),BigDecimal.ZERO,1.0f,cutoffDay);
        Context.gateway.persist(credit);
        creditId = credit.getId();
    }

    @Given("^something was bought for \"([^\"]*)\"with shares of (\\d+)$")
    public void something_was_bought_for_with_shares_of(String amount, int shares) {
        CheckoutCredit checkoutCredit = new CheckoutCredit();
        checkoutCredit.execute(new CheckoutCreditRequest(new BigDecimal(amount),"test",creditId,shares));
    }

    @When("^number of months passed by (\\d+)$")
    public void number_of_months_passed_by(int months) {
        TimeContext.timeHandler.change(new IncreaseMonthHandler(), months);

    }

    @Then("^I see the interest amount \"([^\"]*)\"$")
    public void i_see_the_interest_amount(String interestAmount) {


    }

}
