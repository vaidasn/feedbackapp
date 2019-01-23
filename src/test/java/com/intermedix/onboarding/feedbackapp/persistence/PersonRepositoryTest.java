package com.intermedix.onboarding.feedbackapp.persistence;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testFindAllPersons() {
        int count = 0;
        for (Person p : personRepository.findAll()) {
            assertNotNull(p.getFirstName());
            assertNotNull(p.getLastName());
            count++;
        }
        assertEquals(15, count);
    }

    @Test
    public void testFindPersonByName() {
        Person person = entityManager.find(Person.class, 1L);
        assertNotNull(person);

        assertNotNull(personRepository.findFirstByFirstNameAndLastName(person.getFirstName(), person.getLastName()));
        assertNull(personRepository.findFirstByFirstNameAndLastName("Random", "Guy"));
    }

}