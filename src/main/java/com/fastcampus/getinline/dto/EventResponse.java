package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private PlaceDto place;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String memo;

    public static EventResponse of(
            Long id,
            PlaceDto place,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(
                id,
                place,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public static EventResponse from(EventDto eventDTO) {
        if (eventDTO == null) { return null; }
        return EventResponse.of(
                eventDTO.getId(),
                eventDTO.getPlaceDto(),
                eventDTO.getEventName(),
                eventDTO.getEventStatus(),
                eventDTO.getEventStartDatetime(),
                eventDTO.getEventEndDatetime(),
                eventDTO.getCurrentNumberOfPeople(),
                eventDTO.getCapacity(),
                eventDTO.getMemo()
        );
    }
}
