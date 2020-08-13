package featuresteps.modules;

import driver.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.pageObject.JsonObjects;

import static com.relevantcodes.extentreports.LogStatus.FAIL;
import static com.relevantcodes.extentreports.LogStatus.PASS;
import static utilities.reportGen.ExtentManager.addScreenShot;
import static utilities.urlManager.ApplicationInvoke.applicationUrl;

/**
 * Created by Sajeev_230526 on 2/2/18.
 * Project : automation-tests
 */


public class Google extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(Google.class);
    private static String jsonObjectFile = "Google.json";


    public void openGoogle() throws Throwable {
        try {

            switch (driverType) {

                case "droid":
                    applicationUrl("");
                    break;
                case "ios":
                    applicationUrl("");
                    break;
            }

            test.log(PASS, "Google Url invoked", "Google Page opened");
        } catch (Exception e) {
            log.error(e.toString());
            test.log(FAIL, "Google Url invoked", "Error in opening Google" +
                    test.addScreenCapture(addScreenShot()));
            throw e;
        }
    }


    public void searchGoogle(String searchString) throws Throwable {
        try {

            switch (driverType) {

                case "droid":
                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchBy", "Demo", "web"))
                            .clear();

                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchBy", "Demo", "web"))
                            .sendKeys(searchString);


                    break;
                case "ios":
                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchBy", "Demo", "web"))
                            .clear();

                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchBy", "Demo", "web"))
                            .sendKeys(searchString);

                    break;
            }

            test.log(PASS, "Enter the search text as ", searchString);
        } catch (Exception e) {
            log.error(e.toString());
            test.log(FAIL, "Enter the search text as ", "Error in entering the search text as " + searchString +
                    test.addScreenCapture(addScreenShot()));
            throw e;
        }
    }


    public void searchButton() throws Throwable {
        try {

            switch (driverType) {

                case "droid":


                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchButton", "Demo", "web"))
                            .click();
                    break;
                case "ios":


                    driver.findElement
                            (JsonObjects.getByValue(jsonObjectFile, "searchButton", "Demo", "web"))
                            .click();
                    break;
            }

            test.log(PASS, "Click on the search button ", "search button clicked");
        } catch (Exception e) {
            log.error(e.toString());
            test.log(FAIL, "Click on the search button ", "Error in clicking the search button" +
                    test.addScreenCapture(addScreenShot()));
            throw e;
        }
    }


}
