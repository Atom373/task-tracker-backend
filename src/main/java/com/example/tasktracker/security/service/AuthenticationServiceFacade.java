package com.example.tasktracker.security.service;

import com.example.tasktracker.security.dto.AuthenticationRequestDto;

public interface AuthenticationServiceFacade {

	String authenticate(AuthenticationRequestDto request);

	long getExpirationTime();
}
