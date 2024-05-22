package com.example.tasktracker.mapper.impl;

import org.springframework.stereotype.Component;

import com.example.tasktracker.dto.RegistrationDto;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.mapper.RegistrationDtoToUserMapper;

@Component
public class RegistrationDtoToUserMapperImpl implements RegistrationDtoToUserMapper {

	@Override
	public User map(RegistrationDto dto) {
		return new User(dto.getEmail(), dto.getPassword());
	}

}
