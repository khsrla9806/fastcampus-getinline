package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse {
    private Long id;
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;

    public static PlaceResponse of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(
                id,
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo
        );
    }

    public static PlaceResponse from(PlaceDto placeDto) {
        if (placeDto == null) { return null; }
        return PlaceResponse.of(
                placeDto.getId(),
                placeDto.getPlaceType(),
                placeDto.getPlaceName(),
                placeDto.getAddress(),
                placeDto.getPhoneNumber(),
                placeDto.getCapacity(),
                placeDto.getMemo()
        );
    }
}
