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
        return getInternalResponseEntity(exception, request, ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException exception, WebRequest request) {
        HttpStatus status = exception.getErrorCode().isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return getInternalResponseEntity(exception, request, exception.getErrorCode(), status);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception exception, WebRequest request) {
        return getInternalResponseEntity(exception, request, ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
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

    private ResponseEntity<Object> getInternalResponseEntity(
            Exception exception,
            WebRequest request,
            ErrorCode errorCode,
            HttpStatus status
    ) {
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
}
