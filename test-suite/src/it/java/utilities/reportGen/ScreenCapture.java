package utilities.reportGen;


import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IRational;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * Usage , takes snapshots of your desktop and
 * encodes them to mp4 video.
 *
 * @author : sajeev rajagopalan
 *         might need to deprecate using humble
 */

public class ScreenCapture extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ScreenCapture.class);
    private final int SECONDS_TO_RUN_FOR = 15;
    boolean recordScreenMp4 = true;
    String completeFileName;
    private IRational FRAME_RATE = IRational.make(3, 1);

    public ScreenCapture(String fileName) {
        completeFileName = fileName;
    }

    public void run() {
        try {
            log.info("started recording ***");
            startRecordMp4();
        } catch (InterruptedException ex) {
            log.info("Video recording Thread Interrupted " + ex.getMessage());
        } catch (Exception ex) {
            log.info("Video recording failure, exception caught " + ex.getMessage());
        }

    }


    public void CapScreenMp4() {
        try {
            final Robot robot = new Robot();
            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            final Rectangle screenBounds = new Rectangle(toolkit.getScreenSize());

            // First, let's make a IMediaWriter to write the file.
            final IMediaWriter writer = ToolFactory.makeWriter(completeFileName);

            // We tell it we're going to add one video stream, with id 0,
            // at position 0, and that it will have a fixed frame rate of
            // FRAME_RATE.
            writer.addVideoStream(0, 0,
                    FRAME_RATE,
                    screenBounds.width, screenBounds.height);

            // Now, we're going to loop
            long startTime = System.nanoTime();

            while (recordScreenMp4) {

                //log.info("About to capture screen");
                // take the screen shot
                BufferedImage screen = robot.createScreenCapture(screenBounds);

                // convert to the right image type
                BufferedImage bgrScreen = convertToType(screen,
                        BufferedImage.TYPE_3BYTE_BGR);

                //log.info("About to encode video");
                // encode the image
                writer.encodeVideo(0, bgrScreen,
                        System.nanoTime() - startTime, TimeUnit.NANOSECONDS);

                // sleep for framerate milliseconds
                Thread.sleep((long) (1000 / FRAME_RATE.getDouble()));
            }
            writer.close();
        } catch (Throwable e) {
            log.error("video recording error occurred: " + e.getMessage());

        }
    }

    /**
     * Convert a {@link BufferedImage} of any type, to {@link BufferedImage} of a
     * specified type. If the source image is the same type as the target type,
     * then original image is returned, otherwise new image of the correct type is
     * created and the content of the source image is copied into the new image.
     *
     * @param sourceImage the image to be converted
     * @param targetType  the desired BufferedImage type
     * @return a BufferedImage of the specifed target type.
     * @see BufferedImage
     */

    private BufferedImage convertToType(BufferedImage sourceImage,
                                        int targetType) {
        BufferedImage image;

        // if the source image is already the target type, return the source image

        if (sourceImage.getType() == targetType)
            image = sourceImage;

            // otherwise create a new image of the target type and draw the new
            // image

        else {
            image = new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;
    }


    public void stopRecordMp4() throws Exception {
        recordScreenMp4 = false;
    }


    public void startRecordMp4() throws Exception {
        // To Start Mp4 recording
        recordScreenMp4 = true;
        CapScreenMp4();
    }

}
