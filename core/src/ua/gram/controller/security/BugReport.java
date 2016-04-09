package ua.gram.controller.security;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import ua.gram.model.prototype.GamePrototype;
import ua.gram.model.prototype.ReportPrototype;
import ua.gram.utils.Json;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BugReport {

    private final GamePrototype gamePrototype;
    private final ReportPrototype prototype;

    public BugReport(GamePrototype gamePrototype) {
        this.gamePrototype = gamePrototype;
        this.prototype = gamePrototype.getParameters().reportConfig;
    }

    public boolean sendReport(String text) {
        Json json = new Json();
        String content = text;
        content += json.prettyPrint(gamePrototype.player);
        content += json.prettyPrint(gamePrototype.getParameters());

        CommandMap.setDefaultCommandMap(buildMailcap());

        Properties properties = buildProperties();

        Session mailSession = Session.getInstance(properties, null);
        mailSession.setDebug(false);

        String logFile = gamePrototype.getParameters().getLogFilePath();

        MimeMultipart multipart;
        try {
            multipart = makeAttachement(logFile);
        } catch (MessagingException e) {
            multipart = new MimeMultipart();
            addError(content, e);
        }

        try {
            Message msg = buildMessage(mailSession, multipart, content);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(
                    (String) properties.get("mail.smtp.host"),
                    (int) properties.get("mail.smtp.port"),
                    (String) properties.get("mail.smtp.user"),
                    (String) properties.get("mail.smtp.pass"));
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            Log.info("Email send successfully!");

            return true;

        } catch (MessagingException e) {
            Log.exc("Could not send email", e);
        }

        return false;
    }

    private MailcapCommandMap buildMailcap() {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        return mc;
    }

    private Properties buildProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", prototype.host);
        properties.put("mail.smtp.user", prototype.sender);
        properties.put("mail.smtp.pass", prototype.password);
        properties.put("mail.smtp.port", prototype.port);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.socketFactory.port", prototype.port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", false);
        properties.put("mail.debug", false);
        return properties;
    }

    private Message buildMessage(Session mailSession, MimeMultipart multipart, String content) throws MessagingException {
        Message msg = new MimeMessage(mailSession);

        msg.setSubject(prototype.title);
        msg.setFrom(new InternetAddress(prototype.sender));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(prototype.recipient));
        msg.setDataHandler(new DataHandler(new ByteArrayDataSource("".getBytes(), "text/plain")));
        msg.setContent(multipart);
        msg.setText(content);

        return msg;
    }

    private void addError(String content, Exception e) {
        Log.buildExceptionMessage(content, e);
    }

    private MimeMultipart makeAttachement(String logFile) throws MessagingException {
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(logFile)));
        messageBodyPart.setFileName(logFile);
        multipart.addBodyPart(messageBodyPart);
        return multipart;
    }
}
