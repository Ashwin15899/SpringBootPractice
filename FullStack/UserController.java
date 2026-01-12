package com.crud.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.entity.UserEntity;
import com.crud.demo.repository.UserRepository;
import com.crud.demo.responses.UserResponse;

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
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<UserResponse> getAllUsers(){
		List<UserEntity> users = userRepository.findAll();
		return ResponseEntity.ok(new UserResponse("Users fetched successfully", users));
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
		UserEntity userFound = userRepository.findById(id).orElse(null);
		return ResponseEntity.ok(new UserResponse("User fetched successfully", userFound));
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserEntity userEntity) {
		UserEntity userFound = userRepository.findById(id).orElse(null);
		if(userFound!=null) {
			
			if(userEntity.getUsername() != null && !(userEntity.getUsername() == "")) 
				userFound.setUsername(userEntity.getUsername());
			
			if(userEntity.getEmail() != null && !(userEntity.getEmail() == ""))
				userFound.setEmail(userEntity.getEmail());
			
			if(userEntity.getPassword() != null && !(userEntity.getPassword() == "")) {
				String hashedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
				userFound.setPassword(hashedPassword);				
			}
			
			if(userEntity.getContact() != null && !(String.valueOf(userEntity.getContact()) == ""))
				userFound.setContact(userEntity.getContact());
			
			if(userEntity.getAddress() != null && !(userEntity.getAddress() == ""))
				userFound.setAddress(userEntity.getAddress());
			
			if(userEntity.getRole() != null && !(userEntity.getRole() == ""))
				userFound.setRole(userEntity.getRole());
			
			if(userEntity.getGender() != null && !(userEntity.getGender() == ""))
				userFound.setGender(userEntity.getGender());
			
			if(userEntity.getDateOfBirth() != null && !(userEntity.getDateOfBirth() == ""))
				userFound.setDateOfBirth(userEntity.getDateOfBirth());
			
			userRepository.save(userFound);
			return ResponseEntity.ok(new UserResponse("User updated successfully", userFound));
		} else {
			return ResponseEntity.badRequest().body("Couldn't update user");
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id) {
		UserEntity deletedUser = userRepository.findById(id).orElse(null);
		if(deletedUser != null) {
			userRepository.deleteById(id);
			return ResponseEntity.ok(new UserResponse("User deleted successfully", deletedUser));			
		} else {
			return ResponseEntity.badRequest().body("Couldn't delete user");
		}
	}
	
	@GetMapping("/searchUser")
	public ResponseEntity<?> searchUser(@RequestParam String keyword) {
		if(keyword == null || keyword.trim().isEmpty())
			return ResponseEntity.badRequest().body("No query received");
		
		String searchPattern = "%" + keyword.trim() + "%";

		List<UserEntity> users = userRepository.searchUsers(searchPattern);
		return ResponseEntity.ok(new UserResponse("Users fetched for the query", users));
	}
}
