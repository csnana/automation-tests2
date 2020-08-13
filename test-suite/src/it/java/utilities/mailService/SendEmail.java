package utilities.mailService;

/**
 * @author sajeev rajagopalan
 * project autoFrame
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.readers.yamlReader;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static utilities.mailService.ZipFile.getZip;

public class SendEmail {

    private static final Logger log = LoggerFactory.getLogger(SendEmail.class);
    private static String projectName, senderAddress,
            recipientAddress, mailServer,
            mailtitle, mailBody,
            mailSubject, mailreportName, mailattachment;
    private static String yamlFile = "config/yaml/settings.yaml";

    static {


        projectName = new yamlReader().getYmlKeyValue("basic", "projectName", yamlFile);
        senderAddress = new yamlReader().getYmlKeyValue("eMail", "senderAddress", yamlFile);
        recipientAddress = new yamlReader().getYmlKeyValue("eMail", "recipientAddress", yamlFile);
        mailServer = new yamlReader().getYmlKeyValue("eMail", "mailServer", yamlFile);
        mailtitle = new yamlReader().getYmlKeyValue("eMail", "mailtitle", yamlFile);
        mailBody = new yamlReader().getYmlKeyValue("eMail", "mailBody", yamlFile);
        mailSubject = new yamlReader().getYmlKeyValue("eMail", "mailSubject", yamlFile);
        mailreportName = new yamlReader().getYmlKeyValue("eMail", "mailreportName", yamlFile);
        mailattachment = new yamlReader().getYmlKeyValue("eMail", "mailattachment", yamlFile);


    }


    public static void eMailSend() {
        String sourceFolder = System.getProperty("user.dir") + mailattachment;
        String destinatioFolder = System.getProperty("user.dir") + "/target/Report.zip";
        getZip(sourceFolder, destinatioFolder);
        try {
            sendQantasMail(destinatioFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendQantasMail(String filename) throws Exception {
        Properties properties;
        Session session;
        properties = new Properties();
        properties.put("mail.smtp.host", mailServer);
        session = Session.getInstance(properties, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderAddress));
        //Sending mail to receipients
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientAddress));

        //set the messsage subject & body
        msg.setSubject(mailSubject + projectName);
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(mailtitle + ",\n" + mailBody);

        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(mailreportName);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        msg.setContent(multipart);

        Transport.send(msg);
        log.info("Email has been sent.");

    }

}
