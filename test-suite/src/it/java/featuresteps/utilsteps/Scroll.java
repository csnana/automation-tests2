package featuresteps.utilsteps;

import driver.BaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */


public class Scroll extends BaseConfig {
    private static final Logger log = LoggerFactory.getLogger(Scroll.class);


    public static void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "down");
            js.executeScript("mobile: scroll", scrollObject);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public static void scrollLeft() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "left");
            js.executeScript("mobile: scroll", scrollObject);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public static void scrollRight() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "right");
            js.executeScript("mobile: scroll", scrollObject);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public static void scrollUp() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "up");
            js.executeScript("mobile: scroll", scrollObject);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


    public static void scrollToElement(String elementstring1, String elementstring2) {
        WebElement string1 = driver.findElement(By.name(elementstring1));
        WebElement string2 = driver.findElement(By.name(elementstring2));
        int x = string1.getLocation().getX();
        int y = string1.getLocation().getY();
        int x1 = string2.getLocation().getX();
        int y1 = string2.getLocation().getY();
        driver.swipe(x, y, x1, y1, 1);
        log.info("scroll to element");
    }

    public static void swipe(int x, int y, int z, int w) {
        driver.swipe(x, y, z, w, 5000);
    }

    public static void scrollDownDroid() {
        int height = driver.manage().window().getSize().getHeight();
        driver.swipe(5, height * 2 / 3, 5, height / 3, 1000);
    }

}
