package sopt.study.testcode.hyunjin.spring.common;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> bindException(BindException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiResponse<Object> body = ApiResponse.error(ResponseError.BAD_REQUEST, message);

        return ResponseEntity
                .status(ResponseError.BAD_REQUEST.getCode())
                .body(body);
    }
}
