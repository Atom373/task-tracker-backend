package com.example.tasktracker.service;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.UserAlreadyExistsException;

public interface UserService {

	User save(User user) throws UserAlreadyExistsException;
	
	User findById(Long id);
	
	User findByEmail(String email);
}
