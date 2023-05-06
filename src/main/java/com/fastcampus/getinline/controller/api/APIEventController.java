package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.dto.APIDataResponse;
import com.fastcampus.getinline.dto.EventDto;
import com.fastcampus.getinline.dto.EventRequest;
import com.fastcampus.getinline.dto.EventResponse;
import com.fastcampus.getinline.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class APIEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive @RequestParam(required = false) Long placeId, // 해당 애노테이션의 옵션을 주면 넣어주지 않아도 문제가 없다.
            @Size(min = 2) @RequestParam(required = false) String eventName,
            @RequestParam(required = false) EventStatus eventStatus,
            // String으로 들어온 날짜 포멧을 LocalDateTime으로 잘 바꿔준다.
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime
    ) {
        log.debug("이벤트 컨트롤러 관찰 = {}", placeId);
        List<EventResponse> responses = eventService
                .getEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime)
                .stream().map(EventResponse::from).collect(Collectors.toList());
        return APIDataResponse.of(responses);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        EventResponse response = eventService.createEvent(EventRequest.toDto(eventRequest));
        return APIDataResponse.of(response);
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        if (eventId.equals(2L)) {
            return APIDataResponse.empty();
        }

        return APIDataResponse.of(EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        ));
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<Void> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<Void> removeEvent(@PathVariable Long eventId) {
        return APIDataResponse.empty();
    }

}
