package utilities.webDrivers;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utilities.webDrivers.DriverConstants.BUNDLEID;
import static utilities.webDrivers.DriverConstants.NORESET;
import static utilities.webDrivers.DriverProps.*;


/**
 * Created by sajeev rajagopalan on 17/08/17.
 */
public class DriverCapabilities {

    private static final Logger log = LoggerFactory.getLogger(DriverCapabilities.class);

    static DesiredCapabilities getCapabilities(String type) {

        if (type.equalsIgnoreCase("ios")) {
            return iosCapabilities();
        } else if (type.equalsIgnoreCase("iosapp")) {
            return iosAppCapabilities();
        } else if (type.equalsIgnoreCase("droid")) {
            return droidCapabilities();
        }

        return null;
    }


    private static DesiredCapabilities iosCapabilities() {

        log.info("iosCapabilities");
        DesiredCapabilities iosCap = new DesiredCapabilities();

        // IOS Simulator Capabilities
        iosCap.setCapability("deviceName", IOSdeviceName);
        iosCap.setCapability("platformName", IOSappiumplatformName);
        iosCap.setCapability("platformVersion", IOSplatformVersion);
        iosCap.setCapability(CapabilityType.BROWSER_NAME, IOSbrowser);
        iosCap.setBrowserName("Safari");
        iosCap.setCapability("noReset", true);


        return iosCap;
    }


    private static DesiredCapabilities iosAppCapabilities() {
        log.info("iosAppCapabilities");
        DesiredCapabilities iosAppCap = new DesiredCapabilities();

        // IOS App Simulator Capabilities
        iosAppCap.setCapability("appium-version", "1.6.4");
        iosAppCap.setCapability("deviceName", IOSdeviceName);
        iosAppCap.setCapability("platformName", IOSappiumplatformName);
        iosAppCap.setCapability("platformVersion", IOSplatformVersion);
        iosAppCap.setCapability("app", IOSappPath + IOSappName);
        iosAppCap.setCapability("noReset", NORESET);
        iosAppCap.setCapability("autoAcceptAlerts", true);

        return iosAppCap;
    }


    private static DesiredCapabilities droidCapabilities() {

        log.info("droidCapabilities");
        DesiredCapabilities droidCap = new DesiredCapabilities();

        // Switch with these capabilities for Real Device
        droidCap.setCapability("deviceName", droidDeviceName);
        droidCap.setCapability("platformName", "Android");
        droidCap.setCapability("version", droidVersion);
        droidCap.setCapability("noReset", NORESET);
        droidCap.setCapability("appPackage", BUNDLEID);
        droidCap.setCapability("appActivity", appActivity);
        droidCap.setCapability("app", DroidappPath + DroidappName);
        droidCap.setCapability("unicodeKeyboard", "true");

        return droidCap;
    }
}
