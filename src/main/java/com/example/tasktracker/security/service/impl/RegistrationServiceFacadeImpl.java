package com.example.tasktracker.security.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.mapper.RegistrationDtoToUserMapper;
import com.example.tasktracker.security.service.RegistrationServiceFacade;
import com.example.tasktracker.security.service.UserService;
import com.example.tasktracker.security.util.JwtUtil;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RegistrationServiceFacadeImpl implements RegistrationServiceFacade {

	private JwtUtil jwtUtil;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private RegistrationDtoToUserMapper mapper;
	
	@Override
	public String register(RegistrationRequestDto dto) {
		User user = mapper.map(dto);
		encodeUsersPassword(user);
		user = userService.save(user);
		return jwtUtil.generateToken(user.getId());
	}

	@Override
	public long getExpirationTime() {
		return jwtUtil.getExpirationTime();
	}
	
	private void encodeUsersPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
}
