package com.example.tasktracker.security.mapper;

import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.entity.User;

public interface RegistrationDtoToUserMapper extends Mapper<RegistrationRequestDto, User> {}