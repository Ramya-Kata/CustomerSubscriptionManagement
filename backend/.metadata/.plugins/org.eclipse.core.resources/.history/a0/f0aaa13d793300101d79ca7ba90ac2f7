package com.ramyakata.microservice.service;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private final String SECRET_KEY = "your-static-secret-key-32-bytes!";

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRoleFromToken(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
}
