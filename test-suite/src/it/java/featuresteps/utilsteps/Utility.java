package featuresteps.utilsteps;

import driver.BaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static featuresteps.utilsteps.ScrollUtil.waitForElementToBeClickable;
import static featuresteps.utilsteps.ScrollUtil.waitForElementToDisplay;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */


public class Utility extends BaseConfig {
    private static final Logger log = LoggerFactory.getLogger(Utility.class);

    /**
     * Method to do A click action on a particular element
     *
     * @param by
     */
    public static void click(By by) {
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
    }

    /**
     * Method to do click on the element with index
     *
     * @param by
     * @param index
     */
    public static void clicks(By by, int index) {
        try {
            waitForElementToBeClickable(by);
            List<WebElement> element = driver.findElements(by);
            element.get(index).click();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * Method to enter the text to the text box
     *
     * @param by
     * @param value
     */
    public static void enterText(By by, String value) {
        waitForElementToBeClickable(by);
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(value);
        log.info("Entered the text as : " + value);

    }

    /**
     * swipe down till the element is visible
     *
     * @param by
     */
    public static void scrollToElement(By by) {
        try {
            for (int var = 0; var < 7; var++) {
                if (waitForElementToDisplay(by, 20)) {
                    break;
                } else {
                    swipe();
                    log.info("swipe " + var);
                }
            }

        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    private static void swipe() {
        Dimension size;
        size = driver.manage().window().getSize();
        int scrollStart = (int) (size.height * 0.5);
        int scrollEnd = (int) (size.height * 0.2);
        driver.swipe(0, scrollStart, 0, scrollEnd, 3000);
    }

    /**
     * Method to return the size of the element
     * If the element is not found then return 0
     *
     * @param by
     * @return
     */

    public static int elementSize(By by) {
        try {
            waitForElementToBeClickable(by);
            return driver.findElements(by).size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * return the text of the particular element in the wep page
     *
     * @param by
     * @return
     */
    public static String getText(By by) {
        try {
            waitForElementToBeClickable(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return the text of the particular element in the wep page which having the same identifier to multiple element's
     * This will identified by the index
     *
     * @param by
     * @return
     */
    public static String getTexts(By by, int index) {
        try {
            waitForElementToBeClickable(by);
            List<WebElement> elements = driver.findElements(by);
            return elements.get(index).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to clear the text field
     *
     * @param by
     */
    public static void clear(By by) {
        try {
            waitForElementToBeClickable(by);
            WebElement element = driver.findElement(by);
            element.clear();
        } catch (Exception e) {
            log.error("Unable to clear the textBox");

        }
    }
}
