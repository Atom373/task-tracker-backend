package com.example.tasktracker.mapper.impl;

import org.springframework.stereotype.Component;

import com.example.tasktracker.dto.RegistrationRequestDto;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.mapper.RegistrationDtoToUserMapper;

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