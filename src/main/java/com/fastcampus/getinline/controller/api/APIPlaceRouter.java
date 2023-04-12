package com.fastcampus.getinline.controller.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration // <-- 함수형 프로그래밍에서는 @RestController를 쓰지 않고, @Configuration을 사용한다.
public class APIPlaceRouter {

    @Bean
    public RouterFunction<ServerResponse> placesRouter(APIPlaceHandler apiPlaceHandler) {
        return route().nest(path("/api/places"), builder -> builder
                .GET("", apiPlaceHandler::getPlaces)
                .POST("", apiPlaceHandler::createPlace)
                .GET("/{placeId}", apiPlaceHandler::getPlace)
                .PUT("/{placeId}", apiPlaceHandler::modifyPlace)
                .DELETE("/{placeId}", apiPlaceHandler::deletePlace)
        ).build();
    }
}
