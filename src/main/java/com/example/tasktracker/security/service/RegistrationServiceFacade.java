package com.example.tasktracker.security.service;

import com.example.tasktracker.security.dto.RegistrationRequestDto;

public interface RegistrationServiceFacade {

	String register(RegistrationRequestDto dto);

	long getExpirationTime();
}
