package com.fastcampus.getinline.controller.api;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

@Component // <-- Handler는 @Component를 사용한다.
public class APIPlaceHandler {

    public ServerResponse getPlaces(ServerRequest request) {
        return ServerResponse.ok().body(List.of("place1", "place2"));
    }

    public ServerResponse createPlace(ServerRequest request) {
        return ServerResponse.created(URI.create("/api/places/1")).body(true);
    } //TODO: 1은 구현할 때 제대로된 값을 넣어주자

    public ServerResponse getPlace(ServerRequest request) {
        return ServerResponse.ok().body("place " + request.pathVariable("placeId"));
    }

    public ServerResponse modifyPlace(ServerRequest request) {
        return ServerResponse.ok().body(true);
    }

    public ServerResponse deletePlace(ServerRequest request) {
        return ServerResponse.ok().body(true);
    }
}
