package featuresteps.common;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import driver.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.reportGen.ExtentManager;
import utilities.webDrivers.DriverFactory;

import static com.relevantcodes.extentreports.LogStatus.FAIL;
import static com.relevantcodes.extentreports.LogStatus.INFO;
import static utilities.webDrivers.DriverConstants.*;


/**
 * @author : sajeev rajagopalan
 *         Project : auto Frame
 *         this class is used to
 *         1. create extentreport with feature file scenario names
 *         2. decide app re-set
 */


public class StartupStep extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(StartupStep.class);
    public static Scenario scenario;
    private static boolean extendManagerStart = false;

    /**
     * Method will remove the app data and make the app open from home page
     *
     * @throws Throwable
     */
    public static void remove_App_State() {
        try {
            switch (BROWSER) {
                case "iosapp":
                    driver.removeApp(BUNDLEID);
                    driver.launchApp();
                    break;
                case "droid":
                    driver.resetApp();
                    break;

            }
            log.info("App reset completed");
            test.log(INFO, "Verify the app removed all the app data",
                    "App data removed successfully");
        } catch (Exception e) {
            test.log(FAIL, "Verify the app removed all the app data",
                    "Unable to remove app data");
        }
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        if (!extendManagerStart) {
            test = ExtentManager.startTest(this.scenario, "", true);
            log.info("scenario name :" + this.scenario.getName());
            extendManagerStart = true;
        }
        NORESET = setNoResetOption();
        log.info("noReset flag = " + NORESET);
        driver = DriverFactory.getDriver();
        if (NORESET)
            remove_App_State();
    }

    @After
    public void extendManagerReset(Scenario scenario) {
        //logout();
        if (extendManagerStart) {
            extendManagerStart = false;
            ExtentManager.stopTest(test);
            // driver.removeApp(BUNDLEID);
        }
        driver.quit();
        log.info("App reset completed ");
    }


}
