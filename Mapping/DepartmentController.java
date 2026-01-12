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

import com.map.demo.entity.DepartmentEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.DepartmentRepository;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@PostMapping("/createDepartment")
	public ResponseEntity<MappingResponse> createDepartment(@RequestBody DepartmentEntity departmentEntity) {
		if(departmentEntity.getEmployees() != null) {
			departmentEntity.getEmployees().forEach(employee -> {
				employee.setDepartment(departmentEntity);
			});
		}
		DepartmentEntity newDepartment = departmentRepository.save(departmentEntity);
		return ResponseEntity.ok(new MappingResponse("Department created successfully", newDepartment));
	}
	
	@GetMapping("/getAllDepartments")
	public ResponseEntity<MappingResponse> getAllDepartments() {
		List<DepartmentEntity> departments = departmentRepository.findAll();
		return ResponseEntity.ok(new MappingResponse("Departments fetched successfully", departments));
	}
	
	@GetMapping("/getDepartmentById/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable long id) {
		try {
			DepartmentEntity department = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department Not found"));
			return ResponseEntity.ok(new MappingResponse("Department fetched successfully", department));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/deleteDepartment/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable long id) {
		try {
			DepartmentEntity department = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department Not found"));
			departmentRepository.deleteById(id);
			return ResponseEntity.ok(new MappingResponse("Department deleted successfully", department));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
