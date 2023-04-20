package com.fastcampus.getinline.service;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.dto.EventDto;
import com.fastcampus.getinline.dto.EventResponse;
import com.fastcampus.getinline.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
    }

    public Optional<EventDto> getEvent(Long evenId) {
        return eventRepository.findEvent(evenId);
    }

    public boolean createEvent(EventDto eventDto) {
        return eventRepository.insertEvent(eventDto);
    }

    public boolean modifyEvent(Long eventId, EventDto eventDto) {
        return eventRepository.updateEvent(eventId, eventDto);
    }

    public boolean deleteEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}