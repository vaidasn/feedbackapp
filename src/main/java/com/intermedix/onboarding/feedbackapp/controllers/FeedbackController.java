package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import com.intermedix.onboarding.feedbackapp.persistence.Person;
import com.intermedix.onboarding.feedbackapp.persistence.PersonRepository;
import com.intermedix.onboarding.feedbackapp.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.Date;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MailService mailService;

    @GetMapping("/feedback")
    @Transactional
    public String feedbackForm(@RequestParam(required = false) Long personId, Model model) {
        if (personId == null) {
            model.addAttribute("feedback", new Feedback());
            return "feedback-new-person";
        }

        Person person = personRepository.findById(personId).get();
        Feedback feedback = new Feedback();
        feedback.setPerson(person);
        model.addAttribute("feedback", feedback);
        return "feedback-existing-person";
    }

    @PostMapping("/feedback")
    @Transactional
    public String feedbackSubmit(@ModelAttribute Feedback feedback) throws MessagingException {
        Person feedbackPerson = feedback.getPerson();
        Person person;
        if (feedbackPerson.getId() < 0L) {
            person = personRepository.save(feedbackPerson);
        } else {
            person = personRepository.findById(feedbackPerson.getId()).get();
        }
        feedback.setPerson(person);
        feedback.setCreated(new Date());
        feedbackRepository.save(feedback);
        mailService.sendFeedbackNotification(feedback);
        return "redirect:/view-feedback";
    }
}
