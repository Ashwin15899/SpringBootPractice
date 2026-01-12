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

import com.map.demo.entity.CourseEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.CourseRepository;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/course")
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@PostMapping("/createCourse")
	public ResponseEntity<MappingResponse> createCourse(@RequestBody CourseEntity courseEntity) {
		CourseEntity newCourse = courseRepository.save(courseEntity);
		return ResponseEntity.ok(new MappingResponse("Course created successfully", newCourse));
	}

	@GetMapping("/getAllCourses")
	public ResponseEntity<MappingResponse> getAllCourses() {
		List<CourseEntity> courses = courseRepository.findAll();
		return ResponseEntity.ok(new MappingResponse("Courses fetched successfully", courses));
	}

	@GetMapping("/getCourseById/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable long id) {
		try {
			CourseEntity course = courseRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Course Not found"));
			return ResponseEntity.ok(new MappingResponse("Course fetched successfully", course));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteCourse/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable long id) {
		try {
			CourseEntity course = courseRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Course Not found"));
			courseRepository.deleteById(id);
			return ResponseEntity.ok(new MappingResponse("Course deleted successfully", course));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
