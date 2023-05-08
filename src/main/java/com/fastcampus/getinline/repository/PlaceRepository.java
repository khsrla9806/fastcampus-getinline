package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlaceRepository extends
        JpaRepository<Place, Long>,
        QuerydslPredicateExecutor<Place> {

}
