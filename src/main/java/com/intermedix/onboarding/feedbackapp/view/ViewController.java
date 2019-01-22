package com.intermedix.onboarding.feedbackapp.view;

import com.intermedix.onboarding.feedbackapp.feedback.Feedback;
import com.intermedix.onboarding.feedbackapp.feedback.FeedbackController;
import com.intermedix.onboarding.feedbackapp.feedback.FeedbackRepository;
import com.intermedix.onboarding.feedbackapp.feedback.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
public class ViewController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/view-feedback";
    }

    @GetMapping("/view-feedback")
    public  String viewAllFeedback(Model model) {
        model.addAttribute("allFeedback",  feedbackRepository.findAll());
        return "view-feedback";
    }

}
