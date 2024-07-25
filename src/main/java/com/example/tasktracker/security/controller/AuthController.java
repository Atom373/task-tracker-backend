package com.example.tasktracker.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasktracker.security.dto.AuthenticationRequestDto;
import com.example.tasktracker.security.dto.AuthenticationResponseDto;
import com.example.tasktracker.security.service.AuthenticationServiceFacade;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
	 
	private AuthenticationServiceFacade authenticationService;
	
	@PostMapping("/auth")
	public ResponseEntity<AuthenticationResponseDto> generateToken(@RequestBody AuthenticationRequestDto request){
		String jwt = authenticationService.authenticate(request);
		long expirationTime = authenticationService.getExpirationTime();
		return ResponseEntity.ok(new AuthenticationResponseDto(jwt, expirationTime));
	}
}
