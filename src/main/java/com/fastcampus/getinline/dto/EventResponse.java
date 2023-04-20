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
    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String memo;

    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }

    public static EventResponse from(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }
        return EventResponse.of(
                eventDto.getPlaceId(),
                eventDto.getEventName(),
                eventDto.getEventStatus(),
                eventDto.getEventStartDateTime(),
                eventDto.getEventEndDateTime(),
                eventDto.getCurrentNumberOfPeople(),
                eventDto.getCapacity(),
                eventDto.getMemo()
        );
    }
}
