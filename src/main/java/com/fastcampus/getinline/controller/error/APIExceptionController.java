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

import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@RestControllerAdvice(annotations = RestController.class) // RestController를 쓰는 클래스로 범위를 좁힘
public class APIExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException exception, WebRequest request) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        // ResponseEntityExceptionHandler의 기능을 사용할 수 있도록 설정해주는 Return
        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(false, errorCode, errorCode.getMessage(exception)),
                HttpHeaders.EMPTY,
                status,
                request
        );

//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(
//                        false,
//                        errorCode,
//                        errorCode.getMessage(exception.getMessage())
//                ));
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception exception, WebRequest request) {
        ErrorCode errorCode = INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(false, errorCode, errorCode.getMessage(exception)),
                HttpHeaders.EMPTY,
                status,
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
