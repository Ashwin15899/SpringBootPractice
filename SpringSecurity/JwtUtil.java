package com.security.demo.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	private SecretKey getSigningKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey())
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
	    try {
	        String username = extractUsername(token);
	        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	    } catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	}
	
	private boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
	    return Jwts.parserBuilder()
	               .setSigningKey(getSigningKey())
	               .build()
	               .parseClaimsJws(token)
	               .getBody()
	               .getExpiration();
	}
}
