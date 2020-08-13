package utilities.pageObject;

/**
 * @author sajeev rajagopalan
 * Project : autoFrame
 * Class refined for property read and extends with BaseConfig
 */

import driver.BaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.PropertyReader;

import java.io.IOException;

import static utilities.pageObject.HighlightElement.elementHighlight;


public class PageObjects extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(PageObjects.class);

    static String propt = "0";
    private static String propertyFile = "pageProperty/object.properties";


    public static WebElement getObject(String object) throws IOException {


        propt = new PropertyReader().propsValue(object, propertyFile);
        String split[] = propt.split("=", 2);

        String propertyName = split[0];
        String propertyValue = split[1];

        log.info("Object Name : " + propertyName);
        log.info("Object Value : " + propertyValue);

        WebElement elem = null;

        switch (propertyName) {

            case "id":
                elem = driver.findElement(By.id(propertyValue));
                break;

            case "xpath":
                elem = driver.findElement(By.xpath(propertyValue));
                break;

            case "name":
                elem = driver.findElement(By.name(propertyValue));
                break;

            case "link":
                elem = driver.findElement(By.linkText(propertyValue));
                break;

            case "class":
                elem = driver.findElement(By.className(propertyValue));
                break;

            case "css":
                elem = driver.findElement(By.cssSelector(propertyValue));
                break;

            default:
                log.info("The property value provided " + propertyName + " is not valid");
                break;
        }
        //To highlight the object
        try {
            elementHighlight(elem);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return elem;

    }


}
