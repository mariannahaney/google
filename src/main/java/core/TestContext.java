package core;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.BrowserUtils;

public class TestContext {
    private WebDriver driver;
    private BrowserUtils browserUtils;
    private HomePage homePage;
    public Scenario scenario;

    public TestContext() {
        driver = new Driver().initializeDriver("chrome");
        browserUtils = new BrowserUtils(driver);
        homePage = new HomePage(driver);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public BrowserUtils getBrowserUtils() {
        return this.browserUtils;
    }

    public HomePage getHomePage() {
        return this.homePage;
    }
}
