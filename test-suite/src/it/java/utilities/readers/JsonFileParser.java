package utilities.readers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;


/**
 * @author sajeev rajagopalan
 *         this class is used for page object parser from object json
 *         last update - 24 Mar 2017
 */


public class JsonFileParser {


    private static String jsonPath = System.getProperty("user.dir") + "/target" +
            "/test-classes/com/tcs/project/integration/pageObjectJson/";

    public static String[] readJsonElementryRepoFile(String jsonFileName,
                                                     String elementName,
                                                     String site,
                                                     String device) {
        JSONParser parser = new JSONParser();

        try {

            JSONArray array = (JSONArray) parser.parse(new FileReader(jsonPath +
                    jsonFileName));

            for (Object o : array) {
                JSONObject pageElement = (JSONObject) o;

                JSONArray jsonArray = (JSONArray) pageElement.get(elementName);

                if (jsonArray != null) {

                    for (Object obj : jsonArray) {
                        JSONObject element = (JSONObject) obj;

                        if (site.equalsIgnoreCase(element.get("site").toString())
                                && (device.equalsIgnoreCase(element.get("device").toString()))) {

                            String identifierType = (String) element.get("identifierType");
                            String identifierValue = (String) element.get("identifierValue");

                            return new String[]{identifierType, identifierValue};
                        }

                    }
                }

            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String readJsonDataRepoFile(String jsonFileName, String value) {
        JSONParser parser = new JSONParser();

        String jsonDataPath = System.getProperty("user.dir") + "/target" +
                "/test-classes/com/tcs/project/integration/testData";

        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader(jsonDataPath +
                    jsonFileName));

            for (Object o : array) {
                JSONObject testData = (JSONObject) o;

                JSONArray jsonArray = (JSONArray) testData.get(value);

                if (jsonArray != null) {

                    for (Object obj : jsonArray) {
                        JSONObject test = (JSONObject) obj;

                        return (String) test.get("identifierValue");
                    }
                }
            }
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

