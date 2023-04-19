package com.fastcampus.getinline.controller.api;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.constant.PlaceType;
import com.fastcampus.getinline.dto.PlaceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {

   private final MockMvc mvc;
   private final ObjectMapper mapper;

   public APIPlaceControllerTest(
           @Autowired MockMvc mvc,
           @Autowired ObjectMapper mapper
   ) {
       this.mvc = mvc;
       this.mapper = mapper;
   }

   @DisplayName("[API][GET] 장소 리스트 조회")
    @Test
    void givenNothing_whenRequestPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray()) // data가 array로 들어왔는가?
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("패스트캠퍼스"))
                .andExpect(jsonPath("$.data[0].address").value("서울시 강남구 강남대로 1234"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-1234-1234"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

   @DisplayName("[API][POST] 장소 등록")
   @Test
   void givenPlaceData_whenPostRequesting_thenReturnsStandardResponse() throws Exception {
       // Given
       PlaceRequest placeRequest = PlaceRequest.of(
               PlaceType.COMMON,
               "패스트캠퍼스",
               "서울시 강남구 강남대로 1234",
               "010-1234-1234",
               30,
               "신장개업"
       );

       // When & Then
       mvc.perform(
               post("/api/places")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(mapper.writeValueAsString(placeRequest))
       )
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
               .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
   }


    @DisplayName("[API][GET] 단일 장소 조회 - 장소가 존재하는 경우")
    @Test
    void givenPlaceAndItsId_whenRequestPlace_thenReturnsPlace() throws Exception {

       int placeId = 1;
        // Given

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap()) // data가 array로 들어왔는가?
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("패스트캠퍼스"))
                .andExpect(jsonPath("$.data.address").value("서울시 강남구 강남대로 1234"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-1234"))
                .andExpect(jsonPath("$.data.capacity").value(30))
                .andExpect(jsonPath("$.data.memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 단일 장소 조회 - 장소가 존재하는 경우")
    @Test
    void givenPlaceAndItsId_whenRequestPlace_thenReturnsEmptyPlace() throws Exception {

        int placeId = 2;
        // Given

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty()) // data가 비어있는가?
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

}