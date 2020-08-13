package utilities.mailService;

/**
 * @author sajeev rajagopalan
 * Project autoFrame
 * Used for creating report zip file
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
    private static final Logger log = LoggerFactory.getLogger(ZipFile.class);
    private static String SOURCE_FOLDER = null; // SourceFolder path
    private List<String> fileList;

    public ZipFile() {
        fileList = new ArrayList<String>();
    }


    public static void getZip(String SourceFolder, String DestinationFolder) {
        SOURCE_FOLDER = SourceFolder;
        ZipFile appZip = new ZipFile();
        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(DestinationFolder);
    }

    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            log.info("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file : this.fileList) {
                log.info("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            log.info("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}