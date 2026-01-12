package com.map.demo.controller;

import java.util.ArrayList;
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

import com.map.demo.entity.EmployeeEntity;
import com.map.demo.exceptions.ResourceNotFoundException;
import com.map.demo.repository.EmployeeRepository;
import com.map.demo.response.EmployeeResponseDTO;
import com.map.demo.response.MappingResponse;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping("/createEmployee")
	public ResponseEntity<MappingResponse> createEmployee(@RequestBody EmployeeEntity employeeEntity) {
		EmployeeEntity newEmployee = employeeRepository.save(employeeEntity);
		return ResponseEntity.ok(new MappingResponse("Employee created successfully", newEmployee));
	}
	
	@GetMapping("/getAllEmployees")
	public ResponseEntity<MappingResponse> getAllEmployees() {
		List<EmployeeEntity> employees = employeeRepository.findAll();
		List<EmployeeResponseDTO> employeeDTOs = new ArrayList<>();
		
		for(EmployeeEntity employee : employees) {
			employeeDTOs.add(new EmployeeResponseDTO(employee));
		}
		return ResponseEntity.ok(new MappingResponse("Employees fetched successfully", employeeDTOs));
	}
	
	@GetMapping("/getEmployeeById/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
		try {
			EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not found"));
			return ResponseEntity.ok(new MappingResponse("Employee fetched successfully", employee));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
		try {
			EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not found"));
			employeeRepository.deleteById(id);
			return ResponseEntity.ok(new MappingResponse("Employee deleted successfully", employee));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
