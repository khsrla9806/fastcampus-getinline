package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    @NotNull @Positive private Long placeId;
    @NotBlank private String eventName;
    @NotNull private EventStatus eventStatus;
    @NotNull private LocalDateTime eventStartDatetime;
    @NotNull private LocalDateTime eventEndDatetime;
    @NotNull @PositiveOrZero private Integer currentNumberOfPeople;
    @NotNull @Positive private Integer capacity;
    private String memo;

    public static EventRequest of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public EventDto toDTO() {
        return EventDto.of(
                null,
                null, // TODO: 여기를 반드시 적절히 고쳐야 사용할 수 있음
                this.getEventName(),
                this.getEventStatus(),
                this.getEventStartDatetime(),
                this.getEventEndDatetime(),
                this.getCurrentNumberOfPeople(),
                this.getCapacity(),
                this.getMemo(),
                null,
                null
        );
    }
}

