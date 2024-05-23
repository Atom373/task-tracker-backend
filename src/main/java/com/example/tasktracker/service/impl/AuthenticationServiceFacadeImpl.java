package com.example.tasktracker.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.tasktracker.dto.AuthenticationRequestDto;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.jwtutil.JwtUtil;
import com.example.tasktracker.service.AuthenticationServiceFacade;
import com.example.tasktracker.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationServiceFacadeImpl implements AuthenticationServiceFacade {

	private JwtUtil jwtUtil;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	
	@Override
	public String authenticate(AuthenticationRequestDto request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword(),
						List.of(new SimpleGrantedAuthority("ROLE_USER"))
				)
		); 
		User user = userService.findByEmail(request.getEmail());
		String jwt = jwtUtil.generateToken(user.getId());
		return jwt;
	}

}
