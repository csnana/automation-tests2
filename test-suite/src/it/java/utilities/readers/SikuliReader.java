package utilities.readers;


import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author sajeev rajagopalan
 *         Project : autoFrame
 *         this class is created to get the sikuli image path
 */


public class SikuliReader {

    private static final Logger log = LoggerFactory.getLogger(SikuliReader.class);

    private static String rootFolder = System.getProperty("user.dir") + "/test-suite/target/test-classes/";


    public static Pattern getImage(String imageName) throws Exception {
        String imgName = Sikuliconfig(imageName);

        String imgPath = "com/tcs/project/integration/sikuliImages/";
        File filepath = new File(rootFolder + imgPath);
        log.info("ImagePath :" + rootFolder);
        String location = filepath.toURI().getPath();


        return new Pattern(location + imgName);

    }

    private static String Sikuliconfig(String keyName) throws Exception {

        String line;
        String propertyFile = "sikuliObjects/SikuliObject.properties";
        line = new PropertyReader().propsValue(keyName, propertyFile);
        String[] splitter = line.split("\\=");
        if (splitter.length >= 2) {
            return splitter[1];
        }
        return null;
    }
}
