package com.intermedix.onboarding.feedbackapp.services.impl;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.Person;
import com.intermedix.onboarding.feedbackapp.services.MailService;
import com.intermedix.onboarding.feedbackapp.services.MailTransport;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "feedback.notification.from.email=Full Name <from@example.com>",
        "feedback.notification.username=from@example.com",
        "feedback.notification.password=secret",
        "feedback.notification.to.email=to@example.com",
        "feedback.notification.subject=Test notification about feedback" })
public class MailServiceImplTest {

    @TestConfiguration
    public static class MailServiceTestContextConfiguration {
        @Bean
        public MailService mailService() {
            return new MailServiceImpl();
        }
    }

    @Autowired
    private MailService mailService;

    @MockBean
    private MailTransport mailTransportMock;

    @Test
    public void sendFeedbackNotification() throws MessagingException {
        Feedback feedback = new Feedback();
        Person person = new Person("First", "Last");
        feedback.setPerson(person);
        feedback.setMessage("Test message");
        String createdAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(feedback.getCreated());
        doAnswer(i -> {
            Message message = i.getArgument(0);
            assertEquals("Full Name <from@example.com>", message.getFrom()[0].toString());
            assertEquals("to@example.com", message.getRecipients(Message.RecipientType.TO)[0].toString());
            assertEquals("Test notification about feedback", message.getSubject());
            assertEquals("Date: " + createdAsString + "\n"
                    + "Person: First Last\n"
                    + "Message:\n"
                    + "Test message", message.getContent());
            return Void.TYPE;
        }).when(mailTransportMock).send(any(Message.class));
        mailService.sendFeedbackNotification(feedback);
        verify(mailTransportMock).send(any(Message.class));
    }

}