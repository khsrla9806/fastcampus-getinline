package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String memo;

    public static EventRequest of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }

    public static EventDto toDto(EventRequest request) {
        if (request == null) {
            return null;
        }
        return EventDto.of(
                request.getPlaceId(),
                request.getEventName(),
                request.getEventStatus(),
                request.getEventStartDateTime(),
                request.getEventEndDateTime(),
                request.getCurrentNumberOfPeople(),
                request.getCapacity(),
                request.getMemo(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}

