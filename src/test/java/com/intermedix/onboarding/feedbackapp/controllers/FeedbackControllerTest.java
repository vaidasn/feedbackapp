package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import com.intermedix.onboarding.feedbackapp.persistence.Person;
import com.intermedix.onboarding.feedbackapp.persistence.PersonRepository;
import com.intermedix.onboarding.feedbackapp.services.MailService;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FeedbackControllerTest {

    @TestConfiguration
    public static class FeedbackControllerTestContextConfiguration {
        @Bean
        public FeedbackController feedbackController() {
            return new FeedbackController();
        }
    }

    @Autowired
    private FeedbackController feedbackController;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    Model testModel;

    @MockBean
    private MailService mailService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void feedbackFormNewPerson() {
        assertEquals("feedback-new-person", feedbackController.feedbackForm(null, testModel));
        verify(testModel).addAttribute(eq("feedback"), any(Feedback.class));
        verifyNoMoreInteractions(personRepository);
        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    public void feedbackFormExistingPerson() {
        Person person1 = new Person(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(testModel.addAttribute(eq("feedback"), any(Feedback.class))).thenAnswer(i -> {
            if ("feedback".equals(i.getArgument(0))) {
                assertSame(person1, ((Feedback) i.getArgument(1)).getPerson());
            }
            return i.getMock();
        });
        assertEquals("feedback-existing-person", feedbackController.feedbackForm(1L, testModel));
        verify(testModel).addAttribute(eq("feedback"), any(Feedback.class));
        verify(personRepository).findById(1L);
        verifyNoMoreInteractions(personRepository);
        verifyNoMoreInteractions(feedbackRepository);
    }

    @Test
    public void feedbackSubmitNewPerson() throws MessagingException {
        Person feedbackPerson = new Person();
        feedbackPerson.setFirstName("First");
        feedbackPerson.setLastName("Last");
        Feedback feedback1 = new Feedback();
        feedback1.setPerson(feedbackPerson);
        feedback1.setMessage("Test message");
        Person person1 = new Person(1L);
        person1.setFirstName("First");
        person1.setLastName("Last");
        when(personRepository.save(feedbackPerson)).thenReturn(person1);
        assertEquals("redirect:/view-feedback", feedbackController.feedbackSubmit(feedback1));
        assertSame(person1, feedback1.getPerson());
        verify(personRepository).save(feedbackPerson);
        verify(feedbackRepository).save(feedback1);
        verifyNoMoreInteractions(personRepository);
        verify(mailService).sendFeedbackNotification(any(Feedback.class));
    }

    @Test
    public void feedbackSubmitExistingPerson() throws MessagingException {
        Person feedbackPerson = new Person(1L);
        Feedback feedback1 = new Feedback();
        feedback1.setPerson(feedbackPerson);
        feedback1.setMessage("Test message");
        Person person1 = new Person(1L);
        person1.setFirstName("First");
        person1.setLastName("Last");
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        assertEquals("redirect:/view-feedback", feedbackController.feedbackSubmit(feedback1));
        assertSame(person1, feedback1.getPerson());
        assertEquals("First", feedback1.getPerson().getFirstName());
        assertEquals("Last", feedback1.getPerson().getLastName());
        verify(personRepository).findById(1L);
        verify(feedbackRepository).save(feedback1);
        verifyNoMoreInteractions(personRepository);
        verify(mailService).sendFeedbackNotification(any(Feedback.class));
    }

}