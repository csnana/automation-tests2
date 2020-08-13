package utilities.pageObject;

import driver.BaseConfig;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.JsonFileParser;

import java.io.IOException;

/**
 * @author sajeev rajagopalan
 *         Project : auto Frame
 *         reading from the json page files and passed across step definition
 */


public class JsonObjects extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(JsonObjects.class);


    public static By getByValue(String jsonFileName, String pageElement, String site, String device) throws IOException {

        String[] elementProperty = JsonFileParser.readJsonElementryRepoFile(jsonFileName, pageElement, site, device);

        String identifierType = elementProperty[0];
        String identifierValue = elementProperty[1];

        log.info("Key = " + identifierType);
        log.info("Value = " + identifierValue);

        By by = null;

        switch (identifierType) {

            case "id":
                by = By.id(identifierValue);
                break;

            case "xpath":
                by = By.xpath(identifierValue);
                break;

            case "name":
                by = By.name(identifierValue);
                break;

            case "link":
                by = By.linkText(identifierValue);
                break;

            case "class":
                by = By.className(identifierValue);
                break;

            case "css":
                by = By.cssSelector(identifierValue);
                break;

            default:
                log.error("The identifier type provided " + identifierType + " is not valid");
                break;
        }


        return by;

    }


}
