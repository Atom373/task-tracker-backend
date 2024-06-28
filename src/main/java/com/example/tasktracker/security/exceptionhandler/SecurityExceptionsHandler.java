package com.example.tasktracker.security.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tasktracker.security.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class SecurityExceptionsHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> userAlreadyExists(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
