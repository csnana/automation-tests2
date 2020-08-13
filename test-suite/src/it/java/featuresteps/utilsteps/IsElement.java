package featuresteps.utilsteps;

import com.google.common.base.Function;
import driver.BaseConfig;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */

public class IsElement extends BaseConfig {


    public static void waitForElementClick(By by) {


        for (int second = 0; ; second++) {
            if (second >= 120) fail("timeout");
            try {
                if (isElementPresent(by))
                    Thread.sleep(2000);
                driver.findElement(by).click();
                break;

            } catch (Exception e) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public static int waitForElement(By by) {

        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (isElementPresent(by))
                    break;

            } catch (Exception e) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return driver.findElements(by).size();

    }


    public static boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(by);
            return false;
        }
    }


    public static void fluentWaitClick(final By by) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement aboutMe = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
        aboutMe.click();

    }


    public static void fluentWait(final By by) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement aboutMe = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });

    }


    public static void waitForScreenToLoad(AppiumDriver lDriver, WebElement element, int seconds) {

        WebDriverWait wait = new WebDriverWait(lDriver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

}
