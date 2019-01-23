package com.intermedix.onboarding.feedbackapp.persistence;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class FeedbackRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    public void testFindFeedbackAndPersons() {
        int count = 0;
        for (Feedback f : feedbackRepository.findAll()) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(20, count);
    }

}