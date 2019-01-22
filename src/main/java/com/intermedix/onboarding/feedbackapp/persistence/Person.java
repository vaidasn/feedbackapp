package com.intermedix.onboarding.feedbackapp.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "persons", indexes = { @Index(name = "person_name_idx", columnList = "first_name, last_name", unique = true) })
public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private Collection<Feedback> relatedFeedback;

    public Person() {
        this.id = -1L;
    }

    public Person(long id) {
        this.id = id;
    }

    public Person(String firstName, String lastName ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "person_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "person")
    public Collection<Feedback> getRelatedFeedback() {
        return relatedFeedback;
    }

    public void setRelatedFeedback(Collection<Feedback> relatedFeedback) {
        this.relatedFeedback = relatedFeedback;
    }
}
