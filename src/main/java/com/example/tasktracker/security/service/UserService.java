package com.example.tasktracker.security.service;

import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.exception.UserAlreadyExistsException;

public interface UserService {

	User save(User user) throws UserAlreadyExistsException;
	
	User findById(Long id);
	
	User findByEmail(String email);
}
