package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.domain.Place;
import com.fastcampus.getinline.dto.APIDataResponse;
import com.fastcampus.getinline.dto.PlaceDto;
import com.fastcampus.getinline.dto.PlaceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                "신장개업"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(PlaceRequest request) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDto> getPlace(@PathVariable Integer placeId) {
        if (placeId.equals(2)) {
            return APIDataResponse.of(null); // 테스트를 위한 임시 코드
        }

        return APIDataResponse.of(PlaceDto.of(
                PlaceType.COMMON,
                "패스트캠퍼스",
                "서울시 강남구 강남대로 1234",
                "010-1234-1234",
                30,
                "신장개업"
        ));
    }

    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId) {
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId) {
        return true;
    }
}
