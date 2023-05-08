package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.dto.ApiDataResponse;
import com.fastcampus.getinline.dto.PlaceDto;
import com.fastcampus.getinline.dto.PlaceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiPlaceController {
    @GetMapping("/places")
    public ApiDataResponse<List<PlaceDto>> getPlaces() {
        return ApiDataResponse.of(List.of(PlaceDto.of(
                PlaceType.COMMON,
                "패스트캠퍼스",
                "서울시 강남구 강남대로 1234",
                "010-1234-1234",
                30,
                "신장개업",
                LocalDateTime.now(),
                LocalDateTime.now()
        )));
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/places")
    public ApiDataResponse<Void> createPlace(PlaceRequest request) {
        return ApiDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public ApiDataResponse<PlaceDto> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return ApiDataResponse.of(null); // 테스트를 위한 임시 코드
        }

        return ApiDataResponse.of(PlaceDto.of(
                PlaceType.COMMON,
                "패스트캠퍼스",
                "서울시 강남구 강남대로 1234",
                "010-1234-1234",
                30,
                "신장개업",
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }

    @PutMapping("/places/{placeId}")
    public ApiDataResponse<Void> modifyPlace(@PathVariable Long placeId, PlaceRequest request) {
        return ApiDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public ApiDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return ApiDataResponse.empty();
    }
}
