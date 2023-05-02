package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: 나중에 default 뺄거임
public interface EventRepository extends JpaRepository<Event, Long> {
    default List<EventDto> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        return List.of();
    }

    default Optional<EventDto> findEvent(Long eventId) {
        return Optional.empty();
    }

    default boolean insertEvent(EventDto eventDto) {
        return false;
    }

    default boolean updateEvent(Long eventId, EventDto eventDto) {
        return false;
    }

    default boolean deleteEvent(Long eventId) {
        return false;
    }
}
