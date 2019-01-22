package com.intermedix.onboarding.feedbackapp.feedback;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findFirstByFirstNameAndLastName(String firstName, String lastName);

}
