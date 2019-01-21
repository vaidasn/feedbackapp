package com.intermedix.onboarding.feedbackapp.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class FeedbackController {

    @GetMapping("/feedback")
    public String feedbackForm(@RequestParam(required = false) Long personId, @RequestParam(required = false) String personFirstName,
            @RequestParam(required = false) String personLastName, Model model) {
        if (personId == null || personFirstName == null || personLastName == null) {
            model.addAttribute("feedback", new Feedback());
            return "feedback-new-person";
        }

        Person person = new Person(personId);
        person.setFirstName(personFirstName);
        person.setLastName(personLastName);
        Feedback feedback = new Feedback();
        feedback.setPerson(person);
        model.addAttribute("feedback", feedback);
        return "feedback-existing-person";
    }

    @PostMapping("/feedback")
    public String feedbackSubmit(@ModelAttribute Feedback feedback) {
        return "redirect:/view-feedback";
    }
}
