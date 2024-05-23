package com.example.tasktracker.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.jwtutil.JwtUtil;
import com.example.tasktracker.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{

	private JwtUtil jwtUtil;
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String jwt = null;
		Long id = null;
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			try {
				id = jwtUtil.getIdFromToken(jwt);
			} catch (SignatureException | ExpiredJwtException ex) {
				System.out.println("Error during jwt parsing " + ex.getMessage());
			}
		}
		
		if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User user = userService.findById(id);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					user, 
					null,
					List.of(new SimpleGrantedAuthority("ROLE_USER"))
			);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		
		filterChain.doFilter(request, response);
	}
	
	
}
