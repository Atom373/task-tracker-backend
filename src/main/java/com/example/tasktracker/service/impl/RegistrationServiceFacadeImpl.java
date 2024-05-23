package com.example.tasktracker.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.tasktracker.dto.RegistrationRequestDto;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.jwtutil.JwtUtil;
import com.example.tasktracker.mapper.RegistrationDtoToUserMapper;
import com.example.tasktracker.service.RegistrationServiceFacade;
import com.example.tasktracker.service.UserService;

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

	private void encodeUsersPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
}
