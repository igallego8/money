package money;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/money",
        glue= {"money"},
        plugin = { "pretty","html:target/cucumber-reports", "json:target/cucumber.json" },
        monochrome = true)
public class BuyWithCreditCard {

}