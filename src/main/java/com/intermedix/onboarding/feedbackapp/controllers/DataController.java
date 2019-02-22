package com.intermedix.onboarding.feedbackapp.controllers;

import com.intermedix.onboarding.feedbackapp.persistence.Feedback;
import com.intermedix.onboarding.feedbackapp.persistence.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class DataController {

    private FeedbackRepository feedbackRepository;

    public DataController(@Autowired FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/data/feedback")
    public Map<String, Object> getFeedbackData(@RequestParam(defaultValue = "-1") int draw, @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "-1") int length, @RequestParam(name = "search[value]", required = false) String searchValue,
            @RequestParam(name = "order[0][column]", defaultValue = "0") int orderColumn,
            @RequestParam(name = "order[0][dir]", required = false) String orderDir) {
        if (draw == -1) {
            return allFeedbackData();
        } else {
            return serverSideFeedbackData(draw, start, length, searchValue, orderColumn, orderDir);
        }
    }

    private Map<String, Object> allFeedbackData() {
        Page<Feedback> feedbackResult = feedbackRepository.findAllPageable(Pageable.unpaged());
        return Collections.singletonMap("data", collectFeedbackData(feedbackResult));
    }

    private Map<String, Object> serverSideFeedbackData(int draw, int start, int length, String searchValue,
            int orderColumn, String orderDir) {
        Page<Feedback> feedbackResult = findFeedback(start, length, searchValue, orderColumn, orderDir);
        Map<String, Object> allFeedback = new HashMap<>();
        allFeedback.put("data", collectFeedbackData(feedbackResult));
        allFeedback.put("draw", draw);
        allFeedback.put("recordsTotal", feedbackRepository.findAllPageable(Pageable.unpaged()).getTotalElements());
        allFeedback.put("recordsFiltered", feedbackResult.getTotalElements());
        allFeedback.put("newestCreated", findNewestCreated());
        return allFeedback;
    }

    private Page<Feedback> findFeedback(int start, int length, String searchValue, int orderColumn, String orderDir) {
        Sort.Direction direction = "desc".equals(orderDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String orderProperty;
        switch (orderColumn) {
            case 0:
                orderProperty = "created";
                break;
            case 1:
                orderProperty = "person.firstName";
                break;
            case 2:
                orderProperty = "person.lastName";
                break;
            case 3:
                orderProperty = "message";
                break;
            default:
                throw new IllegalArgumentException();
        }
        Pageable pageable = length > 0 ?
                PageRequest.of(start / length, length, direction, orderProperty) : new UnpagedRequest(direction, orderProperty);
        return searchValue != null && searchValue.length() > 0 ?
                feedbackRepository.findFilteredPageable(toFilter(searchValue), pageable) : feedbackRepository.findAllPageable(pageable);
    }

    private String toFilter(String searchValue) {
        return '%' + searchValue.replace("\\", "\\\\")
                .replace("%", "\\%").replace("_", "\\_") + '%';
    }

    private static Collection<Object> collectFeedbackData(Page<Feedback> feedbackResult) {
        Collection<Object> collectedFeedbackData = new ArrayList<>();
        for (Feedback feedback : feedbackResult) {
            Collection<Object> feedbackData = new ArrayList<>();
            feedbackData.add(feedback.getCreated().getTime());
            feedbackData.add(feedback.getPerson().getFirstName());
            feedbackData.add(feedback.getPerson().getLastName());
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("feedbackId", feedback.getId());
            messageData.put("personId", feedback.getPerson().getId());
            String message = feedback.getMessage();
            messageData.put("message", message);
            feedbackData.add(messageData);
            collectedFeedbackData.add(feedbackData);
        }
        return collectedFeedbackData;
    }

    private long findNewestCreated() {
        Page<Feedback> newestCreatedPage =
                feedbackRepository.findAllPageable(PageRequest.of(0, 1, Sort.Direction.DESC, "created"));
        Iterator<Feedback> newestCreatedIterator = newestCreatedPage.iterator();
        return newestCreatedIterator.hasNext() ? newestCreatedIterator.next().getCreated().getTime() : 0;
    }

}
