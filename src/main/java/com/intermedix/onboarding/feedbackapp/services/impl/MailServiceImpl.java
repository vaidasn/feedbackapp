package com.intermedix.onboarding.feedbackapp.services.impl;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.services.MailService;
import com.intermedix.onboarding.feedbackapp.services.MailTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Value("${feedback.notification.from.email:}")
    private String fromEmail;

    @Value("${feedback.notification.hostname:smtp.gmail.com}")
    private String notificationHostname;

    @Value("${feedback.notification.hostname.port:587}")
    private String notificationHostnamePort;

    @Value("${feedback.notification.username:}")
    private String fromUsername;

    @Value("${feedback.notification.password:}")
    private String fromPassword;

    @Value("${feedback.notification.to.email:}")
    private String toEmail;

    @Value("${feedback.notification.subject:}")
    private String emailSubject;

    @Autowired
    private MailTransport mailTransport;

    @Override
    public void sendFeedbackNotification(Feedback feedback) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", notificationHostname);
        props.put("mail.smtp.port", notificationHostnamePort);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromUsername, fromPassword);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
        message.setSubject(emailSubject, "UTF-8");
        message.setText(String.format(
                "Date: %tF %<tR\n" +
                "Person: %s %s\n" +
                "Message:\n" +
                "%s", feedback.getCreated(), feedback.getPerson().getFirstName(), feedback.getPerson().getLastName(),
                feedback.getMessage()), "UTF-8", "plain");

        mailTransport.send(message);
    }

}
