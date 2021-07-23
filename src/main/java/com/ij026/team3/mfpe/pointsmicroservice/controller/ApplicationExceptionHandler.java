package com.ij026.team3.mfpe.pointsmicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.RetryableException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(RetryableException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleRetryableException(RetryableException exception) {
		return ResponseEntity.badRequest().body(exception.getLocalizedMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleAllRunTimeException(RuntimeException exception) {
		return ResponseEntity.badRequest().body(exception.getLocalizedMessage());
	}
}
