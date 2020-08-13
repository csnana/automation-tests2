package utilities.readers;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;


/**
 * C@author sajeev rajagopalan
 * Project : autoFrame
 * this class help us to read the properties from the yaml file
 */
public class yamlReader {


    private static final Logger log = LoggerFactory.getLogger(yamlReader.class);
    public static String ChldKey;

    public String getYmlKeyValue(String parentKey, String childKey, String yamlFile) {
        String ymlPath = System.getProperty("user.dir") + "/target" +
                "/test-classes/com/tcs/project/integration/" + yamlFile;

        YamlReader reader = null;
        ChldKey = childKey;

        try {
            reader = new YamlReader(new FileReader(ymlPath));
        } catch (FileNotFoundException e) {

            ymlPath = System.getProperty("user.dir") + "/test-suite/target" +
                    "/test-classes/com/tcs/project/integration/" + yamlFile;
            try {
                reader = new YamlReader(new FileReader(ymlPath));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        while (true) {
            Map KeyValue = null;
            try {
                KeyValue = (Map) (reader != null ? reader.read() : null);
            } catch (YamlException e) {
                e.printStackTrace();
            }
            if (KeyValue == null) break;
            String keyVal = KeyValue.get(parentKey).toString();
            //log.info(keyVal);
            if (KeyValue.get(parentKey) == null) {
                break;
            } else {
                String elements[] = keyVal.split(",");
                return ymlKey(elements);
            }
        }
        return null;
    }


    public String getYmlKeyValue(String parentKey, String childKey, String subChildKey, String yamlFile) {

        String ymlPath = System.getProperty("user.dir") + "/target" +
                "/test-classes/com/tcs/project/integration/" + yamlFile;

        YamlReader reader = null;
        ChldKey = childKey;

        try {
            reader = new YamlReader(new FileReader(ymlPath));
        } catch (FileNotFoundException e) {

            ymlPath = System.getProperty("user.dir") + "/test-suite/target" +
                    "/test-classes/com/tcs/project/integration/" + yamlFile;
            try {
                reader = new YamlReader(new FileReader(ymlPath));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        while (true) {
            Map KeyValue = null;
            Map ChildKey = null;
            try {
                KeyValue = (Map) (reader != null ? reader.read() : null);
            } catch (YamlException e) {
                e.printStackTrace();
            }
            if (KeyValue == null) break;
            ChildKey = (Map) KeyValue.get(parentKey);
            //log.info(keyVal);
            String keyVal = ChildKey.get(childKey).toString();

            if (ChildKey.get(childKey) == null) {
                break;
            } else {
                String elements[] = keyVal.split(",");
                return ymlKey(elements);
            }
        }
        return null;
    }


    public String ymlKey(String[] elems) {

        for (String s : elems) {
            String keyelm[] = s.split("=");
            String ymlKey = keyelm[0].trim().replaceAll("\\{", "");
            String ymlKeyValue = keyelm[1].trim().replaceAll("\\}", "");
            if (ChldKey.equalsIgnoreCase(ymlKey)) {
                log.info("Key = " + ymlKey);
                log.info("Value = " + ymlKeyValue);
                return ymlKeyValue;
            }
        }
        return null;
    }

}
