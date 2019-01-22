package com.intermedix.onboarding.feedbackapp.feedback;

import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
