package utilities.urlManager;

import driver.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.relevantcodes.extentreports.LogStatus.PASS;
import static utilities.reportGen.ExtentManager.getTestPassScreenshot;
import static utilities.urlManager.UrlManager.getUrlForApplication;

/**
 * @author sajeev rajagopalan
 *         Project : autoFrame
 */


public class ApplicationInvoke extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationInvoke.class);

    //Application Invoke -url from system variable
    public static void applicationUrl(String testUrlToken) throws Throwable {

        String baseUrl = getUrlForApplication() + testUrlToken;
        driver.navigate().to(baseUrl);
        test.log(PASS, "Url accessed", baseUrl + getTestPassScreenshot());

    }


}
