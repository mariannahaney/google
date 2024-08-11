package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

    @RunWith(Cucumber.class)
    @CucumberOptions(
            plugin = {
                    "pretty",
                    "html:target/report.html",
                    "json:target/cucumber-reports/cucumber.json"
            },
            features = "src/test/resources/features",
            glue = "stepDefs",
            stepNotifications = true,
            tags = "@US101 or @US102",
            dryRun = false
    )
    public class CukesRunner {

    }
