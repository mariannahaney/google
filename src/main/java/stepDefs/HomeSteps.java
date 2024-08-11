package stepDefs;

import core.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class HomeSteps {

    private TestContext testContext;

    public HomeSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("{string} is visible")
    public void is_visible(String item) {
        switch (item) {
            case "Search field":
                Assert.assertTrue(testContext.getHomePage().searchField.isDisplayed());
                testContext.getBrowserUtils().logFailScreenshot(testContext.scenario);
                break;
            default:
                Assert.fail();
        }
    }

    @When("I input {string} in search field")
    public void i_input_in_search_field(String input) {
    switch (input){
        case "aws":
            testContext.getHomePage().searchField.sendKeys(input);
            testContext.getHomePage().searchField.submit();
            break;
    }
    }


    @Then("Verify the results contain word {string}")
    public void verify_the_results_contain_word(String word) {
        Assert.assertTrue(testContext.getHomePage().containsWordInResults(word));
        testContext.getBrowserUtils().logFailScreenshot(testContext.scenario);
    }
}
