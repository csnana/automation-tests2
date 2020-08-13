package driver;

import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.webDrivers.DriverFactory;

/**
 * @author sajeev rajagopalan
 *         project : auto frame
 *         Please do not modify unless we need to use them across modules
 */

public class BaseConfig {

    public static final String driverType = (DriverFactory.DEFAULT_BROWSER).toLowerCase();
    private static final Logger log = LoggerFactory.getLogger(BaseConfig.class);
    public static AppiumDriver driver;
    public static ExtentTest test;
    public static JavascriptExecutor js;

}
