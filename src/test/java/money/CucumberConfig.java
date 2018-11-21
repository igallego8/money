package money;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/money/",
        plugin = { "pretty","html:target/cucumber-html-reports","json:target/cucumber.json"},
        format = { "json:target/cucumber.json"},
        monochrome = true)
public class CucumberConfig {

}