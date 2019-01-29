package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/data/feedback")
    public Map<String, Object> allFeedbackData(HttpServletRequest servletRequest) {
        Collection<Object> allFeedbackData = new ArrayList<>();
        for (Feedback feedback : feedbackRepository.findAll()) {
            Collection<Object> feedbackData = new ArrayList<>();
            feedbackData.add(feedback.getCreated().getTime());
            feedbackData.add(feedback.getPerson().getFirstName());
            feedbackData.add(feedback.getPerson().getLastName());
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("feedbackId", feedback.getId());
            messageData.put("personId", feedback.getPerson().getId());
            String message = feedback.getMessage();
            messageData.put("message", message);
            feedbackData.add(messageData);
            allFeedbackData.add(feedbackData);
        }
        return Collections.singletonMap("data", allFeedbackData);
    }

}
