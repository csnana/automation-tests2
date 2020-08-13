package driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;

import static utilities.mailService.SendEmail.eMailSend;

/**
 * @author sajeev rajagopalan
 *         Project : autoFrame
 *         this class will wrap up the task as part of the post integration phase
 */
public class WrapUp {

    private static final Logger log = LoggerFactory.getLogger(WrapUp.class);

    private static Boolean EmailReport = false;
    private static String yamlFile = "config/yaml/settings.yaml";


    public static void wrap() {

        EmailReport = Boolean.valueOf(new yamlReader().getYmlKeyValue("ExtentReport", "EmailReport", yamlFile));

        if (EmailReport) {
            log.info("Send eMail : Yes");
            eMailSend();
        } else {
            log.info("Send eMail : No");
        }

    }


}
