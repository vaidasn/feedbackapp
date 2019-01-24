package com.intermedix.onboarding.feedbackapp.services;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MailTransport {
    void send(Message msg) throws MessagingException;
}
