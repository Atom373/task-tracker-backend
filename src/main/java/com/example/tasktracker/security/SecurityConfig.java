package com.example.tasktracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.security.filter.JwtRequestFilter;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private JwtRequestFilter jwtRequestFilter;
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager configureAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository repo) {
		return email -> {
			User user = repo.findByEmail(email);
			if (user != null)
				return user;
			throw new UsernameNotFoundException("User with email " + email + " not found");
		};
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf( csrf -> csrf.disable()
				)
				.authorizeHttpRequests( requests -> requests
						.requestMatchers("/api/auth", "/api/register").permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement( sessionManagement -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.exceptionHandling( exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				) 
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}