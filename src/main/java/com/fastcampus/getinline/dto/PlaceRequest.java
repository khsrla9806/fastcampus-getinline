package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequest {
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;

    public static PlaceRequest of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceRequest(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}
