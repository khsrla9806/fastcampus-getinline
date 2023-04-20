package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.domain.Place;
import com.fastcampus.getinline.dto.APIDataResponse;
import com.fastcampus.getinline.dto.PlaceDto;
import com.fastcampus.getinline.dto.PlaceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class APIPlaceController {
    @GetMapping("/places")
    public APIDataResponse<List<PlaceDto>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDto.of(
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
    public APIDataResponse<Void> createPlace(PlaceRequest request) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDto> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return APIDataResponse.of(null); // 테스트를 위한 임시 코드
        }

        return APIDataResponse.of(PlaceDto.of(
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
    public APIDataResponse<Void> modifyPlace(@PathVariable Long placeId, PlaceRequest request) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return APIDataResponse.empty();
    }
}
