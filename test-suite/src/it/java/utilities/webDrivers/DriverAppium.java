package utilities.webDrivers;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static utilities.webDrivers.DriverProps.APPIUMSERVERPATH;
import static utilities.webDrivers.DriverProps.NODEPATH;


/**
 * Created by sajeev rajagopalan on 17/08/17.
 */

public class DriverAppium {

    private static final Logger log = LoggerFactory.getLogger(DriverAppium.class);
    private static AppiumServer appiumServer;

    public static void appiumSetUp() throws IOException, InterruptedException {

        ServerArguments serverArguments = new ServerArguments();
        serverArguments.setArgument("--address", "0.0.0.0");
        serverArguments.setArgument("--port", "4723");
        serverArguments.setArgument("--local-timezone", false);
        serverArguments.setArgument("--native-instruments-lib", true);

        //  Start appium APP
        appiumServer = new AppiumServer
                (new File(NODEPATH), new File(APPIUMSERVERPATH), serverArguments);

        log.info("Starting the Appium server...");
        appiumServer.startServer(60 * 1000);

    }


    public static void appiumTearDown() {

        ServerArguments serverArguments = new ServerArguments();
        appiumServer = new AppiumServer
                (new File(NODEPATH), new File(APPIUMSERVERPATH), serverArguments);

        if (appiumServer.isServerRunning()) {
            log.info("Stopping the Appium Server...");
            appiumServer.stopServer();
        }
    }
}
