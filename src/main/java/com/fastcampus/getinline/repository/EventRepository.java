package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EventRepository extends
        JpaRepository<Event, Long>,
        QuerydslPredicateExecutor<Event> {
}
