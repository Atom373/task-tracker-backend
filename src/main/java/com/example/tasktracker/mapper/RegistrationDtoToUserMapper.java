package com.example.tasktracker.mapper;

import com.example.tasktracker.dto.RegistrationRequestDto;
import com.example.tasktracker.entity.User;

public interface RegistrationDtoToUserMapper extends Mapper<RegistrationRequestDto, User> {}