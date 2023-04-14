package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public ModelAndView general(GeneralException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(exception)
                ),
                status
        );
    }

    @ExceptionHandler
    public ModelAndView general(Exception exception) {
        ErrorCode errorCode = INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(exception)
                ),
                status
        );
    }
}
