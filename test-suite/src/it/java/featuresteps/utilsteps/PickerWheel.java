package featuresteps.utilsteps;

import driver.BaseConfig;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by sajeev rajagopalan
 * framework utility updates
 */

public class PickerWheel extends BaseConfig {
    private static final Logger log = LoggerFactory.getLogger(PickerWheel.class);
    static int x, y, h, w, touchX, touchY;
    @iOSFindBy(xpath = "//XCUIElementTypePickerWheel")
    private static WebElement picker;

    public PickerWheel() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static void scrollToValueDown(int index) {
        try {
            Thread.sleep(3000);
            x = picker.getLocation().getX();
            y = picker.getLocation().getY();
            h = picker.getSize().getHeight();
            w = picker.getSize().getWidth();
            touchX = x + (w / 2);
            touchY = y + (h / 2);
            TouchAction touchAction = new TouchAction(driver);
            for (int var1 = 1; var1 <= index; var1++) {
                touchAction.tap(touchX, touchY + 30).perform();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


    public static void scrollToValueUp(int index) {
        try {
            Thread.sleep(3000);
            x = picker.getLocation().getX();
            y = picker.getLocation().getY();
            h = picker.getSize().getHeight();
            w = picker.getSize().getWidth();
            touchX = x + (w / 2);
            touchY = y + (h / 2);
            TouchAction touchAction = new TouchAction(driver);

            for (int var1 = 1; var1 <= index; var1++) {
                touchAction.tap(touchX, touchY - 30).perform();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    public static ArrayList<String> returnListOfCard() {
        try {
            ArrayList<String> availableCard = new ArrayList<>();
            String beforeTabValue, afterTabValue;
            String[] beforeTabText, afterTabText = null;
            Thread.sleep(3000);
            TouchAction touchAction = new TouchAction(driver);

            while (true) {
                beforeTabValue = picker.getAttribute("value");
                beforeTabText = beforeTabValue.split(",");
                log.info("Before Tab Text " + beforeTabText[0]);
                touchAction.tap(picker.getLocation().getX() + (picker.getSize().getWidth() / 2),
                        picker.getLocation().getY() + (picker.getSize().getHeight() / 2) + 30).perform();
                afterTabValue = picker.getAttribute("value");
                afterTabText = afterTabValue.split(",");
                log.info("After Tab Text " + afterTabText[0]);
                if (beforeTabText[0].equalsIgnoreCase(afterTabText[0])) {
                    break;
                }
                availableCard.add(afterTabText[0]);
            }
            return availableCard;
        } catch (Exception e) {
            log.error("Unable to get the attribute from picker wheel" + e.toString());
        }
        return null;
    }

    public void selectValueFromPicker(String text) {
        try {
            Thread.sleep(3000);
            x = picker.getLocation().getX();
            y = picker.getLocation().getY();
            h = picker.getSize().getHeight();
            w = picker.getSize().getWidth();
            touchX = x + (w / 2);
            touchY = y + (h / 2);
            String value = picker.getAttribute("value");
            String[] dropDownText = value.split(",");
            TouchAction touchAction = new TouchAction(driver);
            while (!dropDownText[0].equalsIgnoreCase(text)) {
                log.info(dropDownText[0]);
                touchAction.tap(touchX, touchY + 30).perform();
                String dropValue = dropDownText[0];
                value = picker.getAttribute("value");
                dropDownText = value.split(",");
                if (dropValue.equalsIgnoreCase(dropDownText[0])) {
                    break;
                }
            }
            while (!dropDownText[0].equalsIgnoreCase(text)) {
                log.info(dropDownText[0]);
                touchAction.tap(touchX, touchY - 30).perform();
                String dropValue = dropDownText[0];
                value = picker.getAttribute("value");
                dropDownText = value.split(",");
                if (dropValue.equalsIgnoreCase(dropDownText[0])) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


}
