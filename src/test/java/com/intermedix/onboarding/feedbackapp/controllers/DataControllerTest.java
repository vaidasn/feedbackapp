package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import com.intermedix.onboarding.feedbackapp.persistence.Person;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DataController.class)
public class DataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Test
    public void allFeedbackDataEmpty() throws Exception {
        mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void allFeedbackDataSingleShort() throws Exception {
        Date feedbackDate = new GregorianCalendar(2019, 0, 11,  12, 47, 32).getTime();
        Feedback singleFeedback = new Feedback(feedbackDate);
        singleFeedback.setId(1);
        singleFeedback.setPerson(new Person("First", "Last"));
        singleFeedback.getPerson().setId(11);
        singleFeedback.setMessage("Short message");
        Mockito.when(feedbackRepository.findAll()).thenReturn(Collections.singleton(singleFeedback));
        mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0]", hasSize(4)))
                .andExpect(jsonPath("$.data[0][0]", is(singleFeedback.getCreated().getTime())))
                .andExpect(jsonPath("$.data[0][1]", is("First")))
                .andExpect(jsonPath("$.data[0][2]", is("Last")))
                .andExpect(jsonPath("$.data[0][3].*", hasSize(3)))
                .andExpect(jsonPath("$.data[0][3].feedbackId", is(1)))
                .andExpect(jsonPath("$.data[0][3].personId", is(11)))
                .andExpect(jsonPath("$.data[0][3].message", is("Short message")));
    }

    @Test
    public void allFeedbackDataSingleLong() throws Exception {
        Date feedbackDate = new GregorianCalendar(2019, 0, 11,  12, 47, 32).getTime();
        Feedback singleFeedback = new Feedback(feedbackDate);
        singleFeedback.setId(1);
        singleFeedback.setPerson(new Person("First", "Last"));
        singleFeedback.getPerson().setId(11);
        singleFeedback.setMessage("Long message long message long message long message long message long message long message");
        Mockito.when(feedbackRepository.findAll()).thenReturn(Collections.singleton(singleFeedback));
        mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0]", hasSize(4)))
                .andExpect(jsonPath("$.data[0][0]", is(singleFeedback.getCreated().getTime())))
                .andExpect(jsonPath("$.data[0][1]", is("First")))
                .andExpect(jsonPath("$.data[0][2]", is("Last")))
                .andExpect(jsonPath("$.data[0][3].*", hasSize(3)))
                .andExpect(jsonPath("$.data[0][3].feedbackId", is(1)))
                .andExpect(jsonPath("$.data[0][3].personId", is(11)))
                .andExpect(jsonPath("$.data[0][3].message", is("Long message long message long message long message long message long message long message")));
    }

}