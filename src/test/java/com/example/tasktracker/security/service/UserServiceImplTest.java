package com.example.tasktracker.security.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.exception.UserAlreadyExistsException;
import com.example.tasktracker.security.repository.UserRepository;
import com.example.tasktracker.security.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private String email = "bob@gmail.com";
	private User user;
	
	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(1);
		user.setEmail(email);
		user.setPassword("1234");
	}
	
	@Test
	public void saveValidUser_shouldSaveUser() {
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User savedUser = userService.save(user);
		
		assertNotNull(savedUser);
		verify(userRepository, times(1)).existsByEmail(anyString());
		verify(userRepository, times(1)).save(user);
	}
	
	@Test
	public void saveUserWithDuplicateEmail_shouldThrowException() {
		when(userRepository.existsByEmail(email)).thenReturn(true);
				
		assertThrows(UserAlreadyExistsException.class, () ->  userService.save(user));
		
		verify(userRepository, times(1)).existsByEmail(email);
		verify(userRepository, never()).save(user);
	}
	
	@Test
	public void findById() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		User obtained = userService.findById(user.getId());
		
		assertNotNull(obtained);
	}
	
	@Test
	public void findByEmail() {
		when(userRepository.findByEmail(email)).thenReturn(user);
		
		User obtained = userService.findByEmail(email);
		
		assertNotNull(obtained);
	}
}