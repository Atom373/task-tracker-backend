package com.example.tasktracker.service.impl;

import org.springframework.stereotype.Component;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.UserAlreadyExistsException;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;
	
	@Override
	public User save(User user) throws UserAlreadyExistsException {
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("Эта почта уже используется");
		}
		return userRepo.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepo.findById(id).orElseThrow( () -> new RuntimeException() );
	}
}
