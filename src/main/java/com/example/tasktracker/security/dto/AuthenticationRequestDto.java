package com.example.tasktracker.security.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

	private String email;
	private String password;
}
