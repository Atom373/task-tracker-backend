package com.example.tasktracker.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

	private String email;
	private String password;
}
