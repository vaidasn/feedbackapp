package com.intermedix.onboarding.feedbackapp.persistence;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void testFindAllFeedbackAndPersons() {
        int count = 0;
        for (Feedback f : feedbackRepository.findAllPageable(Pageable.unpaged())) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(20, count);
    }

    @Test
    public void testFindSecondPageFeedbackAndPersons() {
        int count = 0;
        for (Feedback f : feedbackRepository.findAllPageable(PageRequest.of(1, 10))) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(10, count);
    }

    @Test
    public void testFindSecondPageFeedbackAndPersonsSortByFirstName() {
        int count = 0;
        for (Feedback f : feedbackRepository.findAllPageable(PageRequest.of(1, 10, Sort.by("p.firstName").ascending()))) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(10, count);
    }

    @Test
    public void testFindFilteredFeedbackAndPersons() {
        int count = 0;
        for (Feedback f : feedbackRepository.findFilteredPageable("%Natasha%", Pageable.unpaged())) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testFindFilteredFeedbackAndPersonsPage2() {
        int count = 0;
        for (Feedback f : feedbackRepository.findFilteredPageable("%oyer%", PageRequest.of(1, 1))) {
            assertNotNull(f.getPerson());
            count++;
        }
        assertEquals(1, count);
    }

}