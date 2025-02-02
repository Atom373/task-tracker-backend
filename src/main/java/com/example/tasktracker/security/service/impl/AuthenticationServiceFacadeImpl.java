package com.example.tasktracker.security.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.tasktracker.security.dto.AuthenticationRequestDto;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.service.AuthenticationServiceFacade;
import com.example.tasktracker.security.service.UserService;
import com.example.tasktracker.security.util.JwtUtil;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationServiceFacadeImpl implements AuthenticationServiceFacade {

	private JwtUtil jwtUtil;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
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

	@Override
	public long getExpirationTime() {
		return jwtUtil.getExpirationTime();
	}
}
