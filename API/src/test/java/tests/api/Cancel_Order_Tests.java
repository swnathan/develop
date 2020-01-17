package tests.api;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/CancelOrder.feature", glue = "tests.api.stepdefinitions")
public class Cancel_Order_Tests {

}