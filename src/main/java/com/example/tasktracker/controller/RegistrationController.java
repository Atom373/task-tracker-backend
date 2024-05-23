package com.example.tasktracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasktracker.dto.RegistrationResponseDto;
import com.example.tasktracker.dto.RegistrationRequestDto;
import com.example.tasktracker.service.RegistrationServiceFacade;

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
