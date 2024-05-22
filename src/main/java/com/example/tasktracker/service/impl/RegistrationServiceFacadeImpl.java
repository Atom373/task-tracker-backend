package com.example.tasktracker.service.impl;

import org.springframework.stereotype.Component;

import com.example.tasktracker.dto.RegistrationDto;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.jwtutil.JwtUtil;
import com.example.tasktracker.mapper.RegistrationDtoToUserMapper;
import com.example.tasktracker.service.RegistrationServiceFacade;
import com.example.tasktracker.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RegistrationServiceFacadeImpl implements RegistrationServiceFacade {

	private UserService userService;
	private RegistrationDtoToUserMapper mapper;
	private JwtUtil jwtUtil;
	
	@Override
	public String register(RegistrationDto dto) {
		User user = mapper.map(dto);
		user = userService.save(user);
		return jwtUtil.generateToken(user.getId());
	}

}
