package money;

import com.gallego.money.UserService;
import com.gallego.money.entity.AppFunction;
import com.gallego.money.entity.Context;
import com.gallego.money.entity.License;
import com.gallego.money.entity.User;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.Assert;

import java.util.List;
import java.util.function.Function;

public class PresentSingleFunctionSteps {


    @Given("^user \"([^\"]*)\"$")
    public void user_user(String userName) {
        // Write code here that turns the phrase above into concrete actions
        User user = new User(userName);
        Context.gateway.persist(user);

    }

    @When("^user \"([^\"]*)\" logged in with license for single functions$")
    public void user_loggin_with_license_for_single_functions(String userName)  {
        User user = Context.gateway.findUserBy(userName);

        License licenseCreateCredit = new License(user, Context.gateway.findAppFunctionBy("Create Credit"));
        License licensePayCredit = new License(user, Context.gateway.findAppFunctionBy("Pay Credit"));
        Context.gateway.persist(licenseCreateCredit);
        Context.gateway.persist(licensePayCredit);


    }

    @Then("^the user \"([^\"]*)\" views the functions  \"([^\"]*)\"$")
    public void the_functions_shown_are(String userName, String functions)  {
        User user = Context.gateway.findUserBy(userName);

        List<String> functionList= Lists.newArrayList(functions.split(","));

        for (String functionName:functionList){
            AppFunction function = Context.gateway.findAppFunctionBy(functionName);
            UserService userService = new UserService();
            Assert.assertTrue("",userService.isLicensedToViewFunction(user,function));
        }

    }
}
