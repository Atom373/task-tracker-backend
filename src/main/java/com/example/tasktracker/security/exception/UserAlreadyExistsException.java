package com.example.tasktracker.security.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2670354881500432286L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
