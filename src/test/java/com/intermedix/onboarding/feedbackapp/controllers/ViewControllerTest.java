package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import com.intermedix.onboarding.feedbackapp.persistence.Person;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ViewControllerTest {

    @TestConfiguration
    public static class ViewControllerTestContextConfiguration {
        @Bean
        public ViewController viewController() {
            return new ViewController();
        }
    }

    @Autowired
    private ViewController viewController;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    Model testModel;

    @Test
    public void redirect() {
        assertEquals("redirect:/view-feedback", viewController.redirect());
    }

    @Test
    public void viewAllFeedback() {
        Person person1 = new Person("First", "Last");
        person1.setId(1);
        Feedback feedback1 = new Feedback();
        feedback1.setId(1);
        feedback1.setPerson(person1);
        feedback1.setMessage("test message");
        Set<Feedback> allFeedback = Collections.singleton(feedback1);
        Mockito.when(feedbackRepository.findAll()).thenReturn(allFeedback);
        assertEquals("view-feedback", viewController.viewAllFeedback(testModel));
        Mockito.verify(testModel).addAttribute("allFeedback", allFeedback);
    }

}