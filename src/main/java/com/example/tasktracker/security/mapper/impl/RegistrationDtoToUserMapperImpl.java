package com.example.tasktracker.security.mapper.impl;

import org.springframework.stereotype.Component;

import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.mapper.RegistrationDtoToUserMapper;

@Component
public class RegistrationDtoToUserMapperImpl implements RegistrationDtoToUserMapper {

	@Override
	public User map(RegistrationRequestDto dto) {
		User user = new User();
		
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		
		return user;
	}

}