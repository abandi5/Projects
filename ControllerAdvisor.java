package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ErrorModel> handleNodataFoundException(NoDataFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorModel(ex.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND.getReasonPhrase()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ErrorModel> invalidDataException(InvalidDataException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorModel(ex.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
	}

}