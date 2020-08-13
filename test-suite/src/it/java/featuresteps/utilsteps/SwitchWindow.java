package featuresteps.utilsteps;

import driver.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */


public class SwitchWindow extends BaseConfig {
    private static final Logger log = LoggerFactory.getLogger(SwitchWindow.class);

    public static void switchWindow() {
        try {
            Thread.sleep(10000);
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                //  log.info("Current url " + driver.getCurrentUrl());
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    public static void switch_To_Window_With_Page_Name(String pageName) {
        try {
            Thread.sleep(10000);
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getCurrentUrl().contains(pageName)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.toString());

        }
    }
}
