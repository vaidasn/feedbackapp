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
    public String feedbackForm(@RequestParam Map<String, String> parameters, Model model) {
        if (parameters.isEmpty()) {
            model.addAttribute("feedback",  new Feedback());
            return "feedback-form";
        }
        return "feedback";
    }

    @PostMapping("/feedback")
    public String feedbackSubmit(@ModelAttribute Feedback feedback) {
        return "feedback";
    }
}
