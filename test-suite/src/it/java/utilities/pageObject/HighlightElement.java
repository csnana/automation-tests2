package utilities.pageObject;


import driver.BaseConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utilities.webDrivers.DriverProps.highlight;

/**
 * @author sajeev rajagopalan
 *         Project : autoFrame
 *         Driver & Js extended form the BaseConfig
 */

public class HighlightElement extends BaseConfig {


    private static final Logger log = LoggerFactory.getLogger(HighlightElement.class);
    private static String yamlFile = "config/yaml/settings.yaml";

    public static void elementHighlight(WebElement element) throws InterruptedException {

        if (highlight) {
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='4px groove red'", element);
            Thread.sleep(20);
            js.executeScript("arguments[0].style.border=''", element);
            log.info("Element Highlighted");
        }

    }
}



