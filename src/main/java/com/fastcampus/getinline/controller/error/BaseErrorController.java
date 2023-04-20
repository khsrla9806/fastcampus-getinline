package com.fastcampus.getinline.controller.error;

import com.fastcampus.getinline.constant.ErrorCode;
import com.fastcampus.getinline.dto.APIErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.fastcampus.getinline.constant.ErrorCode.BAD_REQUEST;
import static com.fastcampus.getinline.constant.ErrorCode.INTERNAL_ERROR;

@Controller
public class BaseErrorController implements ErrorController {
    @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE) // Html에 해당하는 View는 여기서 Exceotion 처리
    public ModelAndView errorHtml(HttpServletResponse response) {
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

    @RequestMapping("/error")
    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) { // Api 요청이 들어왔을 때는 여기로 Exception 처리
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? BAD_REQUEST : INTERNAL_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false,
                        errorCode
                ));
    }
}
