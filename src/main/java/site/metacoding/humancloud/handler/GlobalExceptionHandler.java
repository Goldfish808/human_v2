package site.metacoding.humancloud.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.metacoding.humancloud.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseDto<?> apiError(Exception e) {
        return new ResponseDto<>(-1, e.getMessage(), null);
    }
}
