package com.example.tasktracker.service;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.UserAlreadyExistsException;

public interface UserService {

	void save(User user) throws UserAlreadyExistsException;
	
	User findById(Long id);
}
