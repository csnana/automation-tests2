package utilities.Interpolation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/*
 * @author  Sajeev Rajagopalan on 30/03/2016.
 * CSV - Data Interpolation on Feature File Scenario Examples
 */

/*
This class is created to perform interpolation of cucumber files
Please do not modify any part of the code, tested in various operating system and working as expected.
 */

public class InterpolateFile {

    private static final Logger log = LoggerFactory.getLogger(InterpolateFile.class);

    public static String rootDirectory;
    public static String filterName = "feature";
    public static String csvSep = "#<<";

    private static String csvPath;
    private static String yamlFile = "config/yaml/settings.yaml";

    /*
     * This function is used to interpolate the feature-file
     * with the number of csv file available in case of interpolation is been selected
     */

    // Method used by Selenium POM csv-generator - profile
    public static void main(String args[]) throws IOException {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("CSV Interpolation Started");
        csvPath = new yamlReader().getYmlKeyValue("Interpolation", "csvPath", yamlFile);

        rootDirectory = System.getProperty("user.dir") + "/test-suite/target/test-classes";

        // if test triggers from bin folder
        if (rootDirectory.contains(File.separator + "bin")) {
            rootDirectory = System.getProperty("user.dir") + "/../test-suite/target/test-classes";
        }
        rootDirectory = rootDirectory.trim();

        log.info("filterName value  = " + filterName);
        log.info("csvSeparator value  = " + csvSep);
        log.info("InterPolation rootDirectory value  = " + rootDirectory);

        ListFilenames(filterName, rootDirectory, csvSep);

    }


    public static void ListFilenames(String filterName, String rootDirectory, String searchString) {

        String[] exte = {filterName, filterName.toUpperCase()};
        Collection<File> files = FileUtils.listFiles(new File(rootDirectory), exte, true);

        for (File file : files) {

            // Stream interrogated for all available tags.
            try {
                log.info("## Feature File ## : " + file.getName().trim());
                // Navigating the content of feature file
                streamContainsString(file, searchString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    // Find all the string matches the pattern (eg : @tags) with the stream
    public static void streamContainsString(File input, String searchString) throws IOException {


        if (csvPath.equalsIgnoreCase("null")) {
            csvPath = rootDirectory;
            log.info("csvPath : " + csvPath);
        }


        // Reading the Feature File for CSV tags <<csv>>
        log.info("Reading Feature File : " + input.getName());
        String cb = readfeatureFile(input).trim();
        cb = cb.replaceAll("#<<", " #<<");
        for (String st : cb.split(" ")) {
            if (st.startsWith(searchString)) {
                log.info(st.replaceAll("(\\r|\\n)", ""));

                // Trim starts with #<<
                String stout = st.replaceAll("^#<<|>>", "");
                stout = stout.trim();
                log.info(stout.replaceAll("(\\r|\\n)", ""));

                // Reading the CSV File for feature file examples
                String ListCSV = ListfilesCSV("csv", csvPath, stout);

                if ((ListCSV.length() > 0)) {

                    String outputstr;
                    InputStream targetStream = null;

                    // Feature File content as a Stream - every time refresh the latest file
                    cb = readfeatureFile(input).trim();
                    byte[] bytes = cb.getBytes("UTF-8");
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

                    // CSV position with #<<filename>> in Feature File
                    byte[] search = st.getBytes("UTF-8");
                    byte[] replacement = ListCSV.getBytes("UTF-8");

                    try {

                        // Replace the stream CSV tags with CSV example export
                        // #<<filename>> Replaced with CSV contents
                        outputstr = InterpolateStream.byteReplace(bis, search, replacement);

                        // Convert the output string (Feature File + CSV Scenario example) to Input Stream
                        targetStream = new ByteArrayInputStream(outputstr.getBytes(StandardCharsets.UTF_8));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Open the feature file for replacement [Replace Original Feature File with targetStream-Feature+CSV]
                    FileOutputStream out = new FileOutputStream(input);

                    // Copy Replace
                    IOUtils.copy(targetStream, out);
                    if (targetStream != null) {
                        targetStream.close();
                    }
                    out.close();


                }


            }
        }


    }


    private static String readfeatureFile(File f) throws IOException {

        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            Integer fileLength = (int) (long) f.length();
            byte[] b = new byte[fileLength];
            int read = 0;
            while (read < b.length) {
                read += fis.read(b, read, b.length - read);
            }
            String text = new String(b);
            return text;
        } else {
            String text = "";
            return text;
        }
    }


    // List all CSV files; based on the CSV filename extract the content
    public static String ListfilesCSV(String filterName, String rootDirectory, String fileName) {

        String[] exte = {filterName, filterName.toUpperCase()};
        //common CLI functions
        Collection<File> files = FileUtils.listFiles(new File(rootDirectory), exte, true);

        String outPut = null;
        for (File file : files) {

            log.info("csv file : " + file.getName().trim());
            // Stream interrogated for all available tags.
            try {

                if (fileName.trim().equalsIgnoreCase(file.getName())) {

                    log.info("csv match : " + fileName);
                    log.info("## Interpolation Started ##");

                    FileInputStream in = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {

                        out.append(" | ");
                        out.append(line);
                        out.append(" | ");
                        out.append("\n");

                    }
                    outPut = out.toString();
                    outPut = outPut.replaceAll(",", " | ");
                    String stout = outPut.replaceAll("(\r)", "");
                    log.info("Identified CSV Examples : " + "\n " + stout.trim());
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outPut;
    }


}

