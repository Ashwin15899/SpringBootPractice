package com.map.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.map.demo.entity.ProfileEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.ProfileRepository;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@PostMapping("/createProfile")
	public ResponseEntity<MappingResponse> createProfile(@RequestBody ProfileEntity profileEntity) {
		ProfileEntity newProfile = profileRepository.save(profileEntity);
		return ResponseEntity.ok(new MappingResponse("Profile created successfully", newProfile));
	}
	
	@GetMapping("/getAllProfiles")
	public ResponseEntity<MappingResponse> getAllProfiles() {
		List<ProfileEntity> profiles = profileRepository.findAll();
		return ResponseEntity.ok(new MappingResponse("Profiles fetched successfully", profiles));
	}
	
	@GetMapping("/getProfileById/{id}")
	public ResponseEntity<?> getProfileById(@PathVariable long id) {
		try {
			ProfileEntity profile = profileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Profile Not found"));
			return ResponseEntity.ok(new MappingResponse("Profile fetched successfully", profile));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
