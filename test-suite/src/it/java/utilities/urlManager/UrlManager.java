package utilities.urlManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;


/**
 * Updated by sajeev rajagopalan on 14/02/2017.
 * Bamboo url input via
 * a. Run integration -u
 * b. Bamboo variable "bambooEnvUrl"
 */

/**
 * abstract away environment and action URLs
 * <p>
 * priority 1 - bambooEnvUrl (via run integration.sh -u or define bamboo env variable as "bambooEnvUrl"
 * priority 2 - testEnvironmentUrl - run integration.sh value - e
 * priority 2 - testEnvironmentUrl - test.properties value
 * <p>
 * todo: add 'useProxy' flag
 */


public class UrlManager {

    private static final Logger log = LoggerFactory.getLogger(UrlManager.class);
    private static final String DEFAULT_TEST_ENV_URL = "http://localhost/";
    // Env
    private static String testEnv;
    private static String propertyFile = "config/test.properties";
    private static String yamlFile = "config/yaml/settings.yaml";

    static {


        if (System.getenv("bambooEnvUrl") != null) {
            testEnv = System.getenv("bambooEnvUrl");
            log.info("UrlManager.Bamboo Url : " + testEnv);

        } else if (System.getenv("testEnvironmentUrl") != null) {
            testEnv = System.getenv("testEnvironmentUrl");
            log.info("UrlManager.testEnv Url " + testEnv);


        } else {

            testEnv = new yamlReader().getYmlKeyValue("Basic", "envUrl", yamlFile);
            log.info("UrlManager.Property Url " + testEnv);

        }

       /* if (!testEnv.endsWith("/"))
            testEnv += "/";*/

    }

    // todo: different URLs depending in useProxy flag

    public static String getUrlForApplication() {
        return testEnv;
    }

}
