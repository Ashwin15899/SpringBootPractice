package com.security.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.entity.UserEntity;
import com.security.demo.repository.UserRepository;
import com.security.demo.responses.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/createUser")
	public ResponseEntity<UserResponse> createUser(@RequestBody UserEntity userEntity) {
		String hashedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
		userEntity.setPassword(hashedPassword);
		UserEntity newUser = userRepository.save(userEntity);
		return ResponseEntity.ok(new UserResponse("User created successfully", newUser));
	}

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable long id) {
		try {
			UserEntity userFound = userRepository.findById(id).orElse(null);
			return ResponseEntity.ok(new UserResponse("User fetched successfully", userFound));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("No user found");
		}
	}

}
