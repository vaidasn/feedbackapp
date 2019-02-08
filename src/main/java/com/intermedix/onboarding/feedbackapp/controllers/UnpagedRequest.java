package com.intermedix.onboarding.feedbackapp.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class UnpagedRequest extends PageRequest {

    public UnpagedRequest(Sort.Direction direction, String... properties) {
        //noinspection deprecation
        super(0, 1, direction, properties);
    }

    public UnpagedRequest(Sort sort) {
        //noinspection deprecation
        super(0, 1, sort);
    }

    @Override
    public boolean isPaged() {
        return false;
    }

}
