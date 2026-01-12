package com.map.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.map.demo.entity.UserEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.UserRepository;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
	  
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/createUser")
	public ResponseEntity<MappingResponse> createUser(@RequestBody UserEntity userEntity) {
		UserEntity newUser = userRepository.save(userEntity);
		return ResponseEntity.ok(new MappingResponse("User created successfully", newUser));
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<MappingResponse> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return ResponseEntity.ok(new MappingResponse("Users fetched successfully", users));
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable long id) {
		try {
			UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
			return ResponseEntity.ok(new MappingResponse("User fetched successfully", user));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		try {
			UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
			userRepository.deleteById(id);
			return ResponseEntity.ok(new MappingResponse("User deleted successfully", user));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
