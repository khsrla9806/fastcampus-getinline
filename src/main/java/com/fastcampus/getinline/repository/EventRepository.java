package com.fastcampus.getinline.repository;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: 나중에 default 뺄거임
public interface EventRepository extends JpaRepository<Event, Long> {

    // Query Method를 사용하는 방법
    List<Event> findByEventNameAndEventStatus(String eventName, EventStatus eventStatus);

    // @Param 애노테이션 사용하는 방법
    List<Event> findByEventNameAndCapacity(@Param("eventName") String eventName, @Param("capacity") int capacity);

    // @Qeury 애노테이션 사용하는 방법 (JPQL을 사용)
    @Query("select e from Event e where e.eventName=:eventName and e.eventStatus=:eventStatus")
    List<Event> getEventsByEventNameAndEventStatus(String eventName, EventStatus eventStatus);

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
