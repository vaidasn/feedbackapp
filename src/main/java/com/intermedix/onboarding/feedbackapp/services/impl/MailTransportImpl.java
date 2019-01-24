package com.intermedix.onboarding.feedbackapp.services.impl;

import com.intermedix.onboarding.feedbackapp.services.MailTransport;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

@Service
public class MailTransportImpl implements MailTransport {

    @Override
    public void send(Message msg) throws MessagingException {
        Transport.send(msg);
    }

}
