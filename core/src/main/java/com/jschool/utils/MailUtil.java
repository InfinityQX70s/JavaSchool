package com.jschool.utils;

import com.jschool.entities.Driver;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by infinity on 24.03.16.
 */
@Component
public class MailUtil {

    private final String from = "mazumisha@gmail.com";
    private final String username = "mazumisha";
    private final String password = "MishaSveta230411";
    private final String host = "smtp.gmail.com";


    public void sendInvitationMail(String driverEmail) throws MessagingException {
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(getProperties(), authenticator);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(driverEmail));
        message.setSubject("Logiweb Invitation");
        message.setContent("<h5>Congratulation!</h5>" +
                "<p>You were successfully added in Logiweb system, you can login using this link: https://logiweb.herokuapp.com/sign","text/html");
        Transport.send(message);
    }

    public void sendShareMail(Driver driver) throws MessagingException {
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        String link = "https://logiweb.herokuapp.com/share/" + driver.getNumber() + "/" + driver.getToken();
        Session session = Session.getInstance(getProperties(), authenticator);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(driver.getUser().getEmail()));
        message.setSubject("Assign at order");
        message.setContent("You were assigned to order, follow this link to watch it: " + link,"text/html");
        Transport.send(message);
    }


    private Properties getProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        return props;
    }
}
