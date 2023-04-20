package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.EventStatus;
import com.fastcampus.getinline.dto.APIDataResponse;
import com.fastcampus.getinline.dto.EventDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class APIEventController {

    @GetMapping("/events")
    public APIDataResponse<List<EventDto>> getEvents() {
        return APIDataResponse.of(List.of(
                EventDto.of(
                    1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2023, 4, 20, 13, 0, 0),
                        LocalDateTime.of(2023, 4, 20, 16, 0, 0),
                        0,
                        24,
                        "마스크를 반드시 착용하세요.",
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        ));
    }

    @PostMapping("/events")
    public Boolean createEvent() {
        return true;
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Long eventId) {
        return "event " + eventId;
    }

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Long eventId) {
        return true;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Long eventId) {
        return true;
    }
}
