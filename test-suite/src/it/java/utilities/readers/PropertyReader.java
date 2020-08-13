package utilities.readers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author sajeev rajagopalan on 31/5/17.
 *         Project : autoFrame
 *         this class reads the propert values from the supplied property files
 */


public class PropertyReader {

    private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);
    private static String configPath = "com/tcs/project/integration/";

    public String propsValue(String keyName, String propsFile) {

        String keyValue = null;

        Properties prop = new Properties();
        Thread currentThread = Thread.currentThread();
        ClassLoader contextClassLoader = currentThread.getContextClassLoader();
        InputStream propertiesStream = null;

        if (keyName.equalsIgnoreCase("parallel")) {

            try {
                propertiesStream = new FileInputStream(new File(propsFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            propertiesStream = contextClassLoader.getResourceAsStream(configPath + propsFile);
        }


        if (propertiesStream != null) {
            try {
                prop.load(propertiesStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            keyValue = prop.getProperty(keyName);

            //  log.info("propKey : " + keyName);
            //  log.info("propValue : " + keyValue);

            //close the stream
            try {
                propertiesStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Properties file not found!
            log.error("WARNING: unable to read properties(" + propsFile + ")..." +
                    "using defaults if available.");
        }

        return keyValue;
    }


}
