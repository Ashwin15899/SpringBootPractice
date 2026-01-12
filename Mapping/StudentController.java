package com.map.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.map.demo.entity.StudentEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.CourseRepository;
import com.map.demo.repository.StudentRepository;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@PostMapping("/createStudent")
	public ResponseEntity<?> createStudent(@RequestBody StudentEntity studentEntity) {
		try {
			Set<CourseEntity> courses = new HashSet<>();
			for(CourseEntity course: studentEntity.getCourses()) {
				CourseEntity existingCourse = courseRepository.findById(course.getCourseId())
						.orElseThrow(()->new ResourceNotFoundException("Entered course not found"));
				courses.add(existingCourse);
			}
		    studentEntity.setCourses(courses);
			StudentEntity newStudent = studentRepository.save(studentEntity);
			return ResponseEntity.ok(new MappingResponse("Student created successfully", newStudent));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/getAllStudents")
	public ResponseEntity<MappingResponse> getAllStudents() {
		List<StudentEntity> students = studentRepository.findAll();
		return ResponseEntity.ok(new MappingResponse("Students fetched successfully", students));
	}
	
	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable long id) {
		try {
			StudentEntity student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student Not found"));
			return ResponseEntity.ok(new MappingResponse("Student fetched successfully", student));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable long id) {
		try {
			StudentEntity student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Student Not found"));
			studentRepository.deleteById(id);
			return ResponseEntity.ok(new MappingResponse("Student deleted successfully", student));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
