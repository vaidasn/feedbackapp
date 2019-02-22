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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
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
        Mockito.when(feedbackRepository.findAllPageable(Pageable.unpaged()))
                .thenReturn(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 1));
        mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void allFeedbackDataSingleShort() throws Exception {
        Feedback singleFeedback = mockSingleFeedback("Short message", Pageable.unpaged());
        ResultActions mvcResult = mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
        assertFeedbackData(singleFeedback.getCreated().getTime(), "Short message", mvcResult);
    }

    @Test
    public void allFeedbackDataSingleLong() throws Exception {
        Feedback singleFeedback = mockSingleFeedback(
                "Long message long message long message long message long message long message long message", Pageable.unpaged());
        ResultActions mvcResult = mvc.perform(get("/data/feedback")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
        assertFeedbackData(singleFeedback.getCreated().getTime(),
                "Long message long message long message long message long message long message long message", mvcResult
        );
    }

    @Test
    public void allFeedbackDataSingleShortServerSide() throws Exception {
        Feedback singleFeedback = mockSingleFeedback("Short message", Mockito.any(Pageable.class));
        ResultActions mvcResult = mvc.perform(get("/data/feedback?draw=123")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(5)));
        assertFeedbackData(singleFeedback.getCreated().getTime(), "Short message", mvcResult);
        mvcResult.andExpect(jsonPath("$.draw", is(123)));
        mvcResult.andExpect(jsonPath("$.recordsTotal", is(1)));
        mvcResult.andExpect(jsonPath("$.recordsFiltered", is(1)));
        mvcResult.andExpect(jsonPath("$.newestCreated", is(singleFeedback.getCreated().getTime())));
    }

    private Feedback mockSingleFeedback(String singleFeedbackMessage, Pageable pageable) {
        Date feedbackDate = new GregorianCalendar(2019, 0, 11, 12, 47, 32).getTime();
        Feedback singleFeedback = new Feedback(feedbackDate);
        singleFeedback.setId(1);
        singleFeedback.setPerson(new Person("First", "Last"));
        singleFeedback.getPerson().setId(11);
        singleFeedback.setMessage(singleFeedbackMessage);
        Mockito.when(feedbackRepository.findAllPageable(pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(singleFeedback), Pageable.unpaged(), 1));
        return singleFeedback;
    }

    private static void assertFeedbackData(long singleFeedbackCreated, String singleFeedbackMessage, ResultActions mvcResult) throws Exception {
        mvcResult.andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0]", hasSize(4)))
                .andExpect(jsonPath("$.data[0][0]", is(singleFeedbackCreated)))
                .andExpect(jsonPath("$.data[0][1]", is("First")))
                .andExpect(jsonPath("$.data[0][2]", is("Last")))
                .andExpect(jsonPath("$.data[0][3].*", hasSize(3)))
                .andExpect(jsonPath("$.data[0][3].feedbackId", is(1)))
                .andExpect(jsonPath("$.data[0][3].personId", is(11)))
                .andExpect(jsonPath("$.data[0][3].message", is(singleFeedbackMessage)));
    }

}