package com.fastcampus.getinline.service;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.domain.Event;
import com.fastcampus.getinline.dto.EventDto;
import com.fastcampus.getinline.dto.EventResponse;
import com.fastcampus.getinline.exception.GeneralException;
import com.fastcampus.getinline.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        try {
            return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
        } catch (RuntimeException e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<EventDto> getEvent(Long evenId) {
        return eventRepository.findEvent(evenId);
    }

    public EventResponse createEvent(EventDto eventDto) {
        Event event = eventRepository.save(Event.of(
                eventDto.getPlaceId(),
                eventDto.getEventName(),
                eventDto.getEventStatus(),
                eventDto.getEventStartDateTime(),
                eventDto.getEventEndDateTime(),
                eventDto.getCurrentNumberOfPeople(),
                eventDto.getCapacity(),
                eventDto.getMemo()
        ));
        return EventResponse.from(eventDto);
    }

    public Boolean modifyEvent(Long eventId, EventDto eventDto) {
        return eventRepository.updateEvent(eventId, eventDto);
    }

    public Boolean deleteEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
