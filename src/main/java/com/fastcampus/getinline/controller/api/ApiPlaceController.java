package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.dto.ApiDataResponse;
import com.fastcampus.getinline.dto.PlaceDto;
import com.fastcampus.getinline.dto.PlaceRequest;
import com.fastcampus.getinline.dto.PlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiPlaceController {
    @GetMapping("/places")
    public ApiDataResponse<List<PlaceResponse>> getPlaces() {
        return ApiDataResponse.of(List.of(PlaceResponse.of(
                1L,
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public ApiDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return ApiDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public ApiDataResponse<PlaceResponse> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return ApiDataResponse.empty();
        }

        return ApiDataResponse.of(PlaceResponse.of(
                placeId,
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        ));
    }

    @PutMapping("/places/{placeId}")
    public ApiDataResponse<Void> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        return ApiDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public ApiDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return ApiDataResponse.empty();
    }
}
