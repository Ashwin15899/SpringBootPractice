package com.security.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.entity.UserEntity;
import com.security.demo.repository.UserRepository;
import com.security.demo.responses.UserResponse;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<UserResponse> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return ResponseEntity.ok(new UserResponse("Users fetched successfully", users));
	}
	
}
