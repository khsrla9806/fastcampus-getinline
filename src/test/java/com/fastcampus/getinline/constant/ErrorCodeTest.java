package com.fastcampus.getinline.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorCodeTest {

    @DisplayName("예외를 받으면, 예외 메시지가 포함된 메시지 출력 테스트")
    @ParameterizedTest(name = "[{index}] {0} ==> {1}")
    @MethodSource
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(ErrorCode errorCode, String expected) {
        // Given
        Exception exception = new Exception("This is a test message.");

        // When
        String actual = errorCode.getMessage(exception);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                Arguments.arguments(ErrorCode.OK,"Ok - This is a test message."),
                Arguments.arguments(ErrorCode.BAD_REQUEST, "Bad request - This is a test message."),
                Arguments.arguments(ErrorCode.INTERNAL_ERROR, "Internal error - This is a test message."),
                Arguments.arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is a test message."),
                Arguments.arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is a test message."),
                Arguments.arguments(ErrorCode.VALIDATION_ERROR, "Validation error - This is a test message.")

        );
    }

    @DisplayName("메시지를 받으면, 예외 메시지가 포함된 메시지 출력 테스트")
    @ParameterizedTest(name = "[{index}] \"{0}\" ==> \"{1}\"")
    @MethodSource
    void givenMessage_whenGettingMessage_thenReturnsMessage(String message, String expected) {
        // Given
        Exception exception = new Exception("This is a test message.");

        // When
        String actual = ErrorCode.OK.getMessage(message);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                Arguments.arguments(null,"Ok"),
                Arguments.arguments("","Ok"),
                Arguments.arguments(" ","Ok"),
                Arguments.arguments("This is a test message.","This is a test message.")

        );
    }

    @DisplayName("에러코드 toString() 메서드 테스트")
    @Test
    void givenErrorCode_whenGettingToString_thenReturnsSimplifiedToString() {
        // Given


        // When
        String result = ErrorCode.INTERNAL_ERROR.toString();

        // Then
        assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");

    }

}
