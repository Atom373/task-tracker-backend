package com.example.tasktracker.dto;

import lombok.Data;

@Data
public class JwtRequestDto {

	private String email;
	private String password;
}
