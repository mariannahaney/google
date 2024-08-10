package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    public WebDriver initializeDriver(String browserType){
        if (!ConfigReader.readProperty("runInSauceLabs").equals("true"))
            return getWebDriver("chrome");
        return getRemoteDriver();
    }

    private WebDriver getWebDriver(String browserType) {
        WebDriver driver = null;
        switch (browserType.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Wrong browser type");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    private WebDriver getRemoteDriver(){
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 11");
        options.setBrowserVersion("latest");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", ConfigReader.readProperty("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", ConfigReader.readProperty("SAUCE_ACCESS_KEY"));

        //Update this to see if your test was run in SauceLabs
        sauceOptions.put("name", "Scenario execution - Test");

        options.setCapability("sauce:options", sauceOptions);

        URL url = null;
        try {
            url = new URL("https://ondemand.saucelabs.com/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return new RemoteWebDriver(url, options);
    }


}
