package utilities.dateStamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sajeev rajagopalan
 *         Project : autoFrame
 */

public class DateTime {

    private static final Logger log = LoggerFactory.getLogger(DateTime.class);

    public static String getCurrentTime() {

        String currentTime = "";
        String day = new SimpleDateFormat("EEE").format(new Date());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        Date date = new Date();
        log.info(dateFormat.format(date));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        String strDate = sdf.format(cal.getTime());
        log.info("Current date in String Format: " + strDate);

        return currentTime;
    }

    public static String timeStamp() {

        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    }


}
