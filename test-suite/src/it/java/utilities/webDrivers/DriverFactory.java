package utilities.webDrivers;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.concurrent.TimeUnit.SECONDS;
import static utilities.webDrivers.DriverProps.driverTimeout;
import static utilities.webDrivers.DriverProps.getBrowser;


/**
 * @author sajeev rajagopalan
 *         <p>
 *         1st browser type from run Integration.sh
 *         2nd from test.property
 */

public class DriverFactory {


    public static final String DEFAULT_BROWSER;
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    public static Boolean mobileDriver = false;
    private static AppiumDriver<WebElement> appiumDriver;
    private static boolean sikuliTest = false;


    static {

        DEFAULT_BROWSER = getBrowser();
        String driverTimeoutProp = System.getenv("driverTimeout");
        log.info("Driver-TimeOut-runIntegration : " + driverTimeoutProp);
        if (driverTimeoutProp != null)
            driverTimeout = Long.valueOf(driverTimeoutProp);
    }


    private static AppiumDriver initiateBrowserWebDriver(String type) {

        try {
            //setting up the capabilities
            DesiredCapabilities capabilities = DriverCapabilities.getCapabilities(type.toLowerCase());
            log.info("driverType: " + type.toLowerCase());

            switch (type.toLowerCase()) {


                // IOS  - Safari Web
                case "ios":
                default:
                    mobileDriver = true;
                    //appiumDriver = (AppiumDriver<WebElement>) new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                    appiumDriver = new IOSDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                    break;

                // IOS App
                case "iosapp":
                    mobileDriver = true;
                    appiumDriver = new IOSDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                    break;

                // Android Device
                case "droid":
                    mobileDriver = true;
                    appiumDriver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
                    break;

                case "sikuli":
                    appiumDriver = null;
                    sikuliTest = true;
                    break;

            }

        } catch (Throwable e) {

            log.info("DriverFactory.initiateBrowserWebDriver" + "Exception: " + e);
            try {
                throw e;
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        if (!sikuliTest)
            appiumDriver.manage().timeouts().implicitlyWait(driverTimeout, SECONDS);

        return appiumDriver;
    }


    public static AppiumDriver getDriver() {
        log.info("DriverFactory:getDriver");
        return initiateBrowserWebDriver(DEFAULT_BROWSER);
    }


}

