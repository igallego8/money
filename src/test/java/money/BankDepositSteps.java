package money;

import com.gallego.money.util.Context;
import com.gallego.money.util.TimeContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class BankDepositSteps {

    private Long savingAccountId;

    @Given("^a saving account with amount of \"([^\"]*)\"$")
    public void a_saving_account_with_amount_of(String amount){
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAmount(new BigDecimal(amount));
        Context.gateway.persist(savingAccount);
        savingAccountId = savingAccount.getId();
    }

    @Given("^a periodic deposit per (\\d+)  of \"([^\"]*)\"$")
    public void a_periodic_deposit_per_of(int periodType, String depositAmount) {
        switch (periodType){
            case 1:
                DepositTask task = new DepositTask();
                task.setTaskParams(Arrays.asList(new DepositTaskParam(savingAccountId, new BigDecimal(depositAmount))));
                DailyTaskService periodicTaskService =  new DailyTaskService(Arrays.asList(task));
                TimeContext.timeHandler.subscribe(periodicTaskService);
                break;
            case 2:
                break;
            default:
        }
    }

    @When("^the time passes by (\\d+)$")
    public void the_time_passes_by(int period) {
        for (int x = 0; x < period; x++) {
            TimeContext.timeHandler.setDate(LocalDate.now().plusDays(1+x));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("^I see the account balance \"([^\"]*)\"$")
    public void i_see_the_account_balance(String balanceAmount)  {
        Assert.assertTrue("Wrong balance amount",Context.gateway.getSavingAccount(savingAccountId).getAmount().compareTo(new BigDecimal(balanceAmount))==0);
    }

    @Then("^ledger must have credit for \"([^\"]*)\"$")
    public void ledger_must_have_credit_for(String creditLedger) {

    }
}
