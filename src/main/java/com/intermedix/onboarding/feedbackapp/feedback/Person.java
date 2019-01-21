package com.intermedix.onboarding.feedbackapp.feedback;

public class Person {

    public static long nextId = 0;
    private long id = ++nextId;
    private String firstName;
    private String lastName;

    public Person() {
    }

    public Person(long id) {
        this.id = id;
    }

    public Person(String firstName, String lastName ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
