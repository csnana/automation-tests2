package utilities.webDrivers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;

import static featuresteps.common.StartupStep.scenario;
import static utilities.webDrivers.DriverFactory.DEFAULT_BROWSER;

/**
 * Created by Sajeev_230526 on 2/2/18.
 * Project : automation-tests
 */
public class DriverConstants {

    public static final Boolean RETRYENABLED;
    public static final Boolean ISDROID;
    public static final Boolean ISIOS;
    public static final String BUNDLEID;
    public static final String BROWSER;

    private static final Logger log = LoggerFactory.getLogger(DriverConstants.class);
    public static Boolean NORESET;
    private static String yamlFile = "config/yaml/settings.yaml";

    static {
        RETRYENABLED = Boolean.valueOf(new yamlReader().getYmlKeyValue("Basic", "envIssueRetryflag", yamlFile));
        log.info("RETRYENABLED option = " + RETRYENABLED);
        ISDROID = isDroid();
        log.info("ISDROID option = " + ISDROID);
        ISIOS = isIos();
        log.info("ISIOS option = " + ISIOS);
        BUNDLEID = new yamlReader().getYmlKeyValue((ISIOS) ? "IOS" : "Droid", "BundleId", yamlFile);
        log.info("Bundle id = " + BUNDLEID);
        BROWSER = System.getProperty("testBrowser", "iosapp").toLowerCase();
    }

    private static Boolean isIos() {
        try {
            return DEFAULT_BROWSER.equals("iosapp");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method  will return true if the driver type is Android
     * It will return false if the driver type is iosapp
     *
     * @return
     */
    public static Boolean isDroid() {
        try {
            return DEFAULT_BROWSER.equals("droid");
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Method to return whether the No Reset option true or false
     *
     * @return
     */
    public static Boolean setNoResetOption() {
        try {
            for (Object o : scenario.getSourceTagNames()) {
                if (o.equals("@reset")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
