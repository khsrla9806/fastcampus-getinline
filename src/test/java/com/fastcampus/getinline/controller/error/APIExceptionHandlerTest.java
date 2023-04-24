package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.dto.APIErrorResponse;
import com.fastcampus.getinline.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import javax.validation.ConstraintViolationException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class APIExceptionHandlerTest {

    private APIExceptionHandler exceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        exceptionHandler = new APIExceptionHandler();
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @DisplayName("Validation 오류 - 응답 데이터 정의")
    @Test
    void givenViolationException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        ConstraintViolationException exception = new ConstraintViolationException(Set.of());

        // When
        ResponseEntity<Object> response = exceptionHandler.validation(exception, webRequest);

        // Then
        getInternalResponseEntity(new ConstraintViolationException(Set.of()), response, ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }


    @DisplayName("General(프로젝트) 오류 - 응답 데이터 정의")
    @Test
    void givenGeneralException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        GeneralException exception = new GeneralException(errorCode);

        // When
        ResponseEntity<Object> response = exceptionHandler.general(exception, webRequest);

        // Then
        getInternalResponseEntity(exception, response, ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DisplayName("기타 오류 - 응답 데이터 정의")
    @Test
    void givenOtherException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        Exception exception = new Exception();

        // When
        ResponseEntity<Object> response = exceptionHandler.exception(exception, webRequest);

        // Then
        getInternalResponseEntity(exception, response, ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void getInternalResponseEntity(
            Exception exception,
            ResponseEntity<Object> response,
            ErrorCode errorCode,
            HttpStatus status
    ) {
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(
                        false,
                        errorCode,
                        exception
                ))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("status", status);
    }

}
