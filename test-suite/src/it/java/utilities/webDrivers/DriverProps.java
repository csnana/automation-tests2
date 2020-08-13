package utilities.webDrivers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;


/**
 * Created by Sajeev_230526 on 25/8/17.
 * Project : this class is to maintain all the properties required for webdriver
 */


public class DriverProps {

    private static final Logger log = LoggerFactory.getLogger(DriverProps.class);
    public static Boolean highlight;

    // information specified below parsed from settings.yaml
    static String NODEPATH = "/usr/local/bin/node";
    static String APPIUMSERVERPATH = "/usr/local/bin/appium";
    static String IOSdeviceName;
    static String IOSappiumplatformName;
    static String IOSplatformVersion;
    static String IOSbrowser;
    static String IOSappPath;
    static String IOSappName;
    static String DroidappPath;
    static String droidDeviceName;
    static String droidVersion;
    static String DroidappName;
    static String appActivity;
    static long driverTimeout = 30;


    public static String getBrowser() {
        String yamlFile = "config/yaml/settings.yaml";
        String env = System.getenv("testEnvironmentUrl");
        env = (env == null || env.isEmpty()) ? "si" : env;
        NODEPATH = new yamlReader().getYmlKeyValue("Appium", "nodepath", yamlFile);
        APPIUMSERVERPATH = new yamlReader().getYmlKeyValue("Appium", "appiumserverpath", yamlFile);
        IOSdeviceName = new yamlReader().getYmlKeyValue("IOS", "deviceName", yamlFile);
        IOSappiumplatformName = new yamlReader().getYmlKeyValue("IOS", "platformName", yamlFile);
        IOSplatformVersion = new yamlReader().getYmlKeyValue("IOS", "platformVersion", yamlFile);
        IOSappPath = new yamlReader().getYmlKeyValue("IOS", "appPath", yamlFile);
        IOSappName = new yamlReader().getYmlKeyValue("IOS", "appName", env, yamlFile);
        droidDeviceName = new yamlReader().getYmlKeyValue("Droid", "deviceName", yamlFile);
        droidVersion = new yamlReader().getYmlKeyValue("Droid", "platformVersion", yamlFile);
        DroidappPath = new yamlReader().getYmlKeyValue("Droid", "appPath", yamlFile);
        DroidappName = new yamlReader().getYmlKeyValue("Droid", "appName", env, yamlFile);
        appActivity = new yamlReader().getYmlKeyValue("Droid", "appActivity", yamlFile);
        highlight = Boolean.valueOf(new yamlReader().getYmlKeyValue("Basic", "highlight", yamlFile));
        String browser = new yamlReader().getYmlKeyValue("WebDriver", "browser", yamlFile);

        // return from run-integration.sh ; else from settings.yaml
        return System.getProperty("testBrowser", browser);
    }
}
