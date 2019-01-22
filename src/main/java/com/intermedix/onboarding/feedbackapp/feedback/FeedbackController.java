package com.intermedix.onboarding.feedbackapp.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PersonRepository personRepository;

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
    public String feedbackSubmit(@ModelAttribute Feedback feedback) {
        Person person = personRepository.save(feedback.getPerson());
        feedback.setPerson(person);
        feedback.setCreated(new Date());
        feedbackRepository.save(feedback);
        return "redirect:/view-feedback";
    }
}
