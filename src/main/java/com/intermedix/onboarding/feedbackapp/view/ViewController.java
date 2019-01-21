package com.intermedix.onboarding.feedbackapp.view;

import com.intermedix.onboarding.feedbackapp.feedback.Feedback;
import com.intermedix.onboarding.feedbackapp.feedback.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
public class ViewController {

    @GetMapping("/")
    public String redirect() {
        return "redirect:/view-feedback";
    }

    @GetMapping("/view-feedback")
    public  String viewAllFeedback(Model model) {
        Collection<Feedback> allFeedback = new ArrayList<>();
        Feedback.nextId = 0L;
        Person.nextId = 0L;
        allFeedback.add(newlyAddedFeedback("Aaliyah", "Griffin",
                "Per omnibus interrupter, linguist marquisette inelegant sea e. Us doctors time am error ibis no.\n"
                        + "\n"
                        + "Quangos unique eons it, dolor es assertion sit ea."));
        allFeedback.add(newFeedback("Af", "Al", "message 01a"));
        allFeedback.add(newFeedback("Bf", "Bl", "message 02a"));
        allFeedback.add(newFeedback("Cf", "Cl", "message 03a"));
        allFeedback.add(newFeedback("Df", "Dl", "message 04a"));
        allFeedback.add(newFeedback("Ef", "El", "message 05a"));
        allFeedback.add(newFeedback("Af", "Al", "message 01b"));
        allFeedback.add(newFeedback("Bf", "Bl", "message 02b"));
        allFeedback.add(newFeedback("Cf", "Cl", "message 03b"));
        allFeedback.add(newFeedback("Df", "Dl", "message 04b"));
        allFeedback.add(newFeedback("Ef", "El", "message 05b"));
        allFeedback.add(newFeedback("Af", "Al", "message 01c"));
        allFeedback.add(newFeedback("Bf", "Bl", "message 02c"));
        allFeedback.add(newFeedback("Cf", "Cl", "message 03c"));
        allFeedback.add(newFeedback("Df", "Dl", "message 04c"));
        allFeedback.add(newFeedback("Ef", "El", "message 05c"));
        allFeedback.add(newlyAddedFeedback("Warren", "Robinson",
                "In corpora cementum cum, man lull dissent in. Ornateness bland it ex enc, est yeti am bongo detract re.\n"
                + "\n"
                + "Dolor unique id has. I man quad exercise, populous possum instructor in me."));
        model.addAttribute("allFeedback",  allFeedback);
        return "view-feedback";
    }

    private Feedback newFeedback(String firstName, String lastName, String message) {
        Feedback f = new Feedback();
        Person person = new Person(firstName, lastName);
        f.setPerson(person);
        f.setMessage(message);
        return f;
    }

    private Feedback newlyAddedFeedback(String firstName, String lastName, String message) {
        Feedback f = new Feedback(new Date());
        Person person = new Person(firstName, lastName);
        f.setPerson(person);
        f.setMessage(message);
        return f;
    }

}
