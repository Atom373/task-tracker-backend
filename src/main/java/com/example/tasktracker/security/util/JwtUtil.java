package com.example.tasktracker.security.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.lifetime}")
	private Duration lifetime;
	
	public String generateToken(Long id) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", id);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + lifetime.toMillis()))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
				.compact();
	}
	
	public Long getIdFromToken(String token) {
		return getClaimsFromToken(token).get("id", Long.class);
	}
	
	public Claims getClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public long getExpirationTime() {
		return Instant.now().getEpochSecond() + lifetime.getSeconds() - 1;
	}
}
