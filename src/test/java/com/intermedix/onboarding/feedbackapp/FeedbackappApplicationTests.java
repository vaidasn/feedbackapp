package com.intermedix.onboarding.feedbackapp;

import com.intermedix.onboarding.feedbackapp.feedback.FeedbackController;
import com.intermedix.onboarding.feedbackapp.view.ViewController;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackappApplicationTests {

    @Autowired
    private FeedbackController feedbackController;

    @Autowired
    private ViewController viewController;

	@Test
	public void contextLoads() {
        assertThat(feedbackController, notNullValue());
        assertThat(viewController, notNullValue());
	}

}
