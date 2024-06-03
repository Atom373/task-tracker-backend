package com.example.tasktracker.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasktracker.security.dto.RegistrationResponseDto;
import com.example.tasktracker.security.service.RegistrationServiceFacade;
import com.example.tasktracker.security.dto.RegistrationRequestDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrationController {

	private RegistrationServiceFacade registrationService;
	
	@PostMapping("/register")
	public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto dto) {
		String jwt = registrationService.register(dto);
		return ResponseEntity.ok(new RegistrationResponseDto(jwt));
	}
}
