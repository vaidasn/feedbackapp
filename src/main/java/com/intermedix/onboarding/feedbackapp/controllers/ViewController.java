package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
