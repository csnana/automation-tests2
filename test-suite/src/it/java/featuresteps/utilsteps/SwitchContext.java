package featuresteps.utilsteps;

import driver.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static featuresteps.utilsteps.SwitchWindow.switchWindow;
import static utilities.webDrivers.DriverConstants.ISDROID;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */


public class SwitchContext extends BaseConfig {
    public static final Boolean ISWEBVIEW = setIswebview();
    private static final Logger log = LoggerFactory.getLogger(SwitchContext.class);

    public static void switchToWeb() {
        try {
            Thread.sleep(10000);
            Set<String> contextNames = driver.getContextHandles();
            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    driver.context(contextName);
                }
            }
            if (ISDROID) {
                switchWindow();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public static void switchToNative() {
        try {
            Thread.sleep(10000);
            Set<String> contextNames = driver.getContextHandles();
            log.info("context names " + contextNames.toString());
            for (String contextName : contextNames) {
                log.info("Available context " + contextName);
                if (contextName.contains("NATIVE")) {
                    driver.context(contextName);
                    log.info("current context " + contextName);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private static Boolean setIswebview() {
        try {
            return driver.getContext().contains("WEBVIEW");
        } catch (Exception e) {
            return false;
        }

    }
}
