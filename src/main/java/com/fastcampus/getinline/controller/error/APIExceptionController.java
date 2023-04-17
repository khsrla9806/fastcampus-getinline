package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.dto.APIErrorResponse;
import com.fastcampus.getinline.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@RestControllerAdvice(annotations = RestController.class) // RestController를 쓰는 클래스로 범위를 좁힘
public class APIExceptionController {
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
