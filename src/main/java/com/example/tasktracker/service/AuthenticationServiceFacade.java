package com.example.tasktracker.service;

import com.example.tasktracker.dto.AuthenticationRequestDto;

public interface AuthenticationServiceFacade {

	String authenticate(AuthenticationRequestDto request);
}
