package driver;

/**
 * @author sajeev rajagopalan
 * project : autoFrame
 */


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utilities.webDrivers.DriverAppium.appiumSetUp;
import static utilities.webDrivers.DriverAppium.appiumTearDown;


@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty",
                "html:target/html/",
                "json:target/json/cucumber.json",
                "junit:target/junit/cucumber_junit.xml"
        },
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-extent/html/index.html"},
        features = {"classpath:com/tcs/project/integration/userstory"},
        glue = {"featuresteps/common", "featuresteps/stepdefs"}

)


public class RunnerTest {

    private static final Logger log = LoggerFactory.getLogger(RunnerTest.class);


    @BeforeClass
    public static void beforeClass() {
        try {
            appiumSetUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() throws Throwable {

        // Kill appium server if started --- issue the command only if the user have sudo
        appiumTearDown();
        WrapUp.wrap();
        log.info("driver terminated");

    }


}
