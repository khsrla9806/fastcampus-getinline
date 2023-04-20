package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.PlaceType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlaceDto {

    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PlaceDto of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new PlaceDto(placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo,
                createdAt,
                modifiedAt
        );
    }
}
