package com.intermedix.onboarding.feedbackapp.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findFirstByFirstNameAndLastName(String firstName, String lastName);

}
