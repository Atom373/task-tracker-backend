package com.example.tasktracker.security.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.exception.UserAlreadyExistsException;
import com.example.tasktracker.security.repository.UserRepository;
import com.example.tasktracker.security.service.UserService;

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

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}
}
