package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.dto.APIErrorResponse;
import com.fastcampus.getinline.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@RestControllerAdvice(annotations = RestController.class) // RestController를 쓰는 클래스로 범위를 좁힘
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * ResponseEntityExceptionHandler를 사용하는 예외처리 코드로 작성
     */
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException exception, WebRequest request) {
        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(
                        false,
                        ErrorCode.VALIDATION_ERROR,
                        ErrorCode.VALIDATION_ERROR.getMessage(exception)
                ),
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException exception, WebRequest request) {
        HttpStatus status = exception.getErrorCode().isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        // ResponseEntityExceptionHandler의 기능을 사용할 수 있도록 설정해주는 Return
        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(
                        false,
                        exception.getErrorCode(),
                        exception.getErrorCode().getMessage(exception)
                ),
                HttpHeaders.EMPTY,
                status,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception exception, WebRequest request) {
        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(
                        false,
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.INTERNAL_ERROR.getMessage(exception)
                ),
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorCode errorCode = status.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;
        return super.handleExceptionInternal(
                ex,
                APIErrorResponse.of(false, errorCode, errorCode.getMessage(ex)),
                headers,
                status,
                request
        );
    }
}
