package com.security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.entity.UserEntity;
import com.security.demo.jwt.JwtUtil;
import com.security.demo.responses.AuthResponse;
import com.security.demo.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@RequestBody UserEntity userEntity) {
		try {

//			To validate credentials
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEntity.getUsername());

			String token = jwtUtil.generateToken(userDetails);
			return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("Login successful", token));
			
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
		}
	}

}
