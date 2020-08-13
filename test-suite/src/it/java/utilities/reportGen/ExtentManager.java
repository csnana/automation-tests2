package utilities.reportGen;

/**
 * @author Sajeev Rajagopalan on 4/12/16.
 * Project : autoframe
 */

import com.cucumber.listener.Reporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import cucumber.api.Scenario;
import driver.BaseConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.relevantcodes.extentreports.LogStatus.INFO;


public class ExtentManager extends BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(ExtentManager.class);
    private static String parallelReport = "target/cucumber-parallel/html/";
    private static String reportLocation = "target/extent-reports/";
    private static String imageLocation = "imagesRepo/";
    private static String castLocation = "castRepo/";
    private static boolean screenCastEnabled = Boolean.valueOf(System.getProperty("recordVideo", "false"));
    private static ExtentReports extent;

    // private static ScreenEncode screenCapture = null;
    private static boolean extentStarted = false;
    private static boolean replaceExisting = false;
    private static List<String> ScenariofailCount = new ArrayList<String>();
    private static List<String> ScenarioCount = new ArrayList<String>();
    private static String repoName = "TATA";
    private static String orgName = "TATA";
    private static Boolean TestPassScreenshot = Boolean.valueOf("false");
    private static String yamlFile = "config/yaml/settings.yaml";

    private static Reporter reporter;
    private static ScreenCapture screenCapture = null;


    static {

        //Starting the console application in a thread
        Thread console = new Thread(() -> {
            try {

                repoName = new yamlReader().getYmlKeyValue("ExtentReport", "reportName", yamlFile);
                orgName = new yamlReader().getYmlKeyValue("ExtentReport", "organisation", yamlFile);
                TestPassScreenshot = Boolean.valueOf(new yamlReader().getYmlKeyValue("ExtentReport", "testPassScreenshot", yamlFile));
                replaceExisting = Boolean.valueOf(new yamlReader().getYmlKeyValue("ExtentReport", "appendReport", yamlFile));


            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        console.start();

    }


    public static ExtentReports getInstance() {
        if (extent == null) {

            extent = new ExtentReports(getReportPath(), !replaceExisting);
            // optional
            extent
                    //.addSystemInfo("Selenium Version", "")
                    .addSystemInfo("Browser", System.getProperty("testBrowser"))
                    //  .addSystemInfo("Browser Version", browserVersion)
                    .addSystemInfo(System.getProperty("testEnv"), System.getProperty("testEnvironment"))
                    .addSystemInfo("Organisation", orgName)
                    .addSystemInfo("Report", repoName);
        }
        return extent;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String getReportPath() {
        //create required directories
        new File(reportLocation).mkdirs();
        new File(reportLocation + imageLocation).mkdirs();
        new File(parallelReport + imageLocation).mkdirs();
        new File(reportLocation + castLocation).mkdirs();
        return reportLocation + "index.html";
    }

    private static String[] getCategories(Scenario scenario) {

        return scenario == null || scenario.getSourceTagNames() == null ? new String[]{} :
                scenario.getSourceTagNames().toArray(new String[]{});
    }

    public static ExtentTest startTest(Scenario scenario, String description, boolean supportScreenCast) {
        ExtentTest test = null;

        ScenarioCount.add("true");
        log.info("Scenarios Tested " + ScenarioCount.size());

        if (!extentStarted) {
            log.info("Extended Test started");
            extentStarted = true;

            test = getInstance().startTest(scenario.getName(), description).assignCategory(getCategories(scenario));

            log.info("Testing " + test);
            if (screenCastEnabled && supportScreenCast)
                test.log(INFO, "screen capture started: <br/>" + test.addScreencast(startVideoRecording()));
        }
        return test;
    }

    public static void stopTest(ExtentTest test) {
        if (test != null) getInstance().endTest(test);
        getInstance().flush();

        if (screenCastEnabled)
            stopVideoRecording();
        if (extentStarted)
            extentStarted = false;

    }

    public static String startVideoRecording() {
        stopVideoRecording();
        String currentVideoName = String.valueOf(UUID.randomUUID()) + ".mp4";
        screenCapture = new ScreenCapture(reportLocation + castLocation + currentVideoName);
        //screenCapture = new ScreenEncode(reportLocation + castLocation + currentVideoName);
        screenCapture.setDaemon(true);
        screenCapture.start();

        log.info("recording started.....");
        return "./" + castLocation + currentVideoName;
    }

    public static void stopVideoRecording() {
        if (screenCapture == null)
            return;

        try {
            screenCapture.stopRecordMp4();
            screenCapture.join();
            screenCapture = null;
            log.info("Video recording stopped");
        } catch (Exception ex) {
            log.error("Video recording failed to Stop " + ex.getMessage());
        }
    }

    public static String getTestPassScreenshot() {
        if (TestPassScreenshot) {

            return test.addScreenCapture(ExtentManager.addScreenShot());
        } else {
            return null;
        }
    }

    public static String addScreenShot() {

        UUID uuid = UUID.randomUUID();
        String screenShotFileName = imageLocation + uuid + ".png";
        if (driver instanceof TakesScreenshot) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(reportLocation + screenShotFileName));
            } catch (IOException e) {
                log.error("Error while generating screen-shot: " + e.getMessage());
            }
            log.info("captured to : " + screenShotFileName);

            return screenShotFileName;
        } else {
            log.warn("screen-shot capability not there.");
        }
        return "";
    }


    public static String addScreenShotSikuli(Screen scr) {

        // update fail count
        ScenariofailCount.add("Fail");
        log.info("Scenario failCount " + ScenariofailCount.size());
        UUID uuid = UUID.randomUUID();
        String screenShotFileName = imageLocation + uuid + ".png";
        String path = new File(reportLocation + imageLocation).getPath();

        try {
            BufferedImage bf = scr.capture().getImage();
            File outputfile = new File(reportLocation + screenShotFileName);
            ImageIO.write(bf, "jpg", outputfile);

        } catch (Exception e) {
            log.error("Error while generating screen-shot: " + e.getMessage());
        }
        log.info("captured to : " + screenShotFileName);
        return screenShotFileName;

    }

    // @ update : Ujwal Keshir ; to capture desktop screenshot
    public static String addDesktopScreenshot() {

        ScenariofailCount.add("Fail");
        log.info("Scenario failCount " + ScenariofailCount.size());
        UUID uuid = UUID.randomUUID();
        String screenShotFileName = imageLocation + uuid + ".png";
        String path = new File(reportLocation + imageLocation).getPath();

        try {

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);
            Robot robot = new Robot();
            BufferedImage originalImage = robot.createScreenCapture(screenRect);
            ImageIO.write(originalImage, "png", new File(reportLocation + screenShotFileName));

            //BufferedImage bf = scr.capture().getImage();
            File outputfile = new File(reportLocation + screenShotFileName);
            //ImageIO.write(bf, "jpg", outputfile);

        } catch (Exception e) {
            log.error("Error while generating screen-shot: " + e.getMessage());
        }

        return screenShotFileName;
    }


}

