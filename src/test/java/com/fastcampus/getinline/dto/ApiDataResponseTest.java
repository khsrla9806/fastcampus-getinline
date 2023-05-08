package com.fastcampus.getinline.dto;

import com.fastcampus.getinline.constant.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// 단위 테스트는 빠르게 진행되어야 한다.
// 스프링 빈이 따로 필요 없기 때문에 별도의 애노테이션이 필요 없다.
class ApiDataResponseTest {
    @DisplayName("문자열 데이터가 주어지면 표준 성공 응답을 생성한다.")
    @Test
    void givenStringData_whenCreatingResponse_thenReturnsSuccessfulResponse() {
        // Given
        String data = "Test Data";

        // When
        ApiDataResponse<String> response = ApiDataResponse.of(data);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);
    }

    @DisplayName("데이터가 없을 때, 비어있는 표준 성공 응답을 생성한다.")
    @Test
    void givenNothing_whenCreatingResponse_thenReturnsSuccessfulEmptyResponse() {
        // Given

        // When
        ApiDataResponse<String> response = ApiDataResponse.empty();

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }
}