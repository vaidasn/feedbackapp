package com.intermedix.onboarding.feedbackapp;

import com.intermedix.onboarding.feedbackapp.services.MailService;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailService mailService;

	@Test
    public void viewFeedbackLoads() throws Exception {
	    mockMvc.perform(get("/view-feedback")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(startsWith("<!DOCTYPE html>")));
    }

    @Test
    public void feedbackNewPersonLoads() throws Exception {
        mockMvc.perform(get("/feedback")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(
                        allOf(containsString("<form"), not(containsString(" disabled ")))));
    }

    @Test
    public void feedbackExistingPersonLoads() throws Exception {
        mockMvc.perform(get("/feedback").param("personId", "1")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(
                        allOf(containsString("<form"), containsString(" disabled "))));
    }

    @Test
    public void feedbackNewPersonSubmits() throws Exception {
        mockMvc.perform(post("/feedback")
                .param("person.firstName", "Firstname")
                .param("person.lastName", "Surname")
                .param("message", "Test message")).andDo(print())
                .andExpect(status().isFound()).andExpect(redirectedUrl("/view-feedback"));
    }

    @Test
    public void feedbackExistingPersonSubmits() throws Exception {
        mockMvc.perform(post("/feedback")
                .param("person.id", "1")
                .param("message", "Test message")).andDo(print())
                .andExpect(status().isFound()).andExpect(redirectedUrl("/view-feedback"));
    }

}
