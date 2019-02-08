package com.intermedix.onboarding.feedbackapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Query(value = "select f from Feedback f left join fetch f.person p",
           countQuery = "select count(f) from Feedback f")
    Page<Feedback> findAllPageable(Pageable pageable);

    @Query(value = "select f from Feedback f left join fetch f.person p "
            + "where f.message like :filter or p.firstName like :filter or p.lastName like :filter",
            countQuery = "select count(f) from Feedback f where f.message like :filter or exists "
                    + "(select p from f.person p where p.firstName like :filter or p.lastName like :filter)")
    Page<Feedback> findFilteredPageable(@Param("filter") String filter, Pageable pageable);

}
