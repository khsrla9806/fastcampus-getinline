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
public class APIExceptionController extends ResponseEntityExceptionHandler {

    /**
     * ResponseEntityExceptionHandler를 사용하는 예외처리 코드로 작성
     */
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException exception, WebRequest request) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        HttpStatus status = HttpStatus.BAD_REQUEST; // 이미 Client-Side인 것을 알기 떄문에

        return super.handleExceptionInternal(
                exception,
                APIErrorResponse.of(
                        false,
                        errorCode,
                        errorCode.getMessage(exception)
                ),
                HttpHeaders.EMPTY,
                status,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> general(GeneralException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false,
                        errorCode,
                        errorCode.getMessage(exception.getMessage())
                ));
    }

    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> exception(Exception exception) {
        ErrorCode errorCode = INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false,
                        errorCode,
                        errorCode.getMessage(exception.getMessage())
                ));
    }
}
