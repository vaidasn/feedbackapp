package com.intermedix.onboarding.feedbackapp.services;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;

import javax.mail.MessagingException;

public interface MailService {

    void sendFeedbackNotification(Feedback feedback) throws MessagingException;

}
