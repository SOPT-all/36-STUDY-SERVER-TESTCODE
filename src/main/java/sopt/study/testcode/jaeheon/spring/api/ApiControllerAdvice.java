package sopt.study.testcode.jaeheon.spring.api;

import java.net.BindException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class ApiControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> bindException(BindException e){
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getLocalizedMessage(),
			null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
			null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ApiResponse<Object> HandlerMethodValidationException(HandlerMethodValidationException e){

		String message = e.getAllErrors().stream()
			.map(MessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.joining());

		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			message,
			null);
	}
}
