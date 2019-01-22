package com.intermedix.onboarding.feedbackapp.persistence;

import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
