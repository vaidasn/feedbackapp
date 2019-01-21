package com.intermedix.onboarding.feedbackapp.feedback;

import java.util.Date;

public class Feedback {

    public static long nextId = 0;
    private long id = ++nextId;
    private Date created;
    private Person person;
    private String message;

    public Feedback() {
        created = new Date(new Date().getTime() - (long)(Math.random() * 31_000_000_000.));
    }

    public Feedback(Date created) {
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
