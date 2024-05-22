package com.example.tasktracker.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2670354881500432286L;
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
