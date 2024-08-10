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
            tags = "@accountTests",
            dryRun = false
    )
    public class CukesRunner {

    }
