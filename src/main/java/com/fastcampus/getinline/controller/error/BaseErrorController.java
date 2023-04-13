package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.fastcampus.getinline.constant.ErrorCode.BAD_REQUEST;
import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@Controller
public class BaseErrorController implements ErrorController {
    @RequestMapping("/error")
    public ModelAndView error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? BAD_REQUEST : INTERNAL_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                   "statusCode", status.value(),
                   "errorCode", errorCode,
                   "message", errorCode.getMessage(status.getReasonPhrase())
                ),
                status
        );
    }
}
