package stepDefs;

import core.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;

public class Hooks {

    private final TestContext testContext;

    public Hooks(TestContext testContext){
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario){
        testContext.scenario = scenario;
        testContext.getDriver().get(ConfigReader.readProperty("url"));
    }

    @After
    public void tearDown(Scenario scenario){
        if (scenario.isFailed()){
            testContext.getBrowserUtils().logFailScreenshot(scenario);
        }
        if (testContext.getDriver() != null)
            testContext.getDriver().quit();
    }
}
