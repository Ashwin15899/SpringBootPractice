package com.map.demo.response;

import com.map.demo.entity.EmployeeEntity;

public class EmployeeResponseDTO {
	private Long employeeId;
	private String employeeName;
	private String departmentName;

	public EmployeeResponseDTO() {
		super();
	}
	
	public EmployeeResponseDTO(EmployeeEntity employee) {
		super();
		this.employeeId = employee.getId();
		this.employeeName = employee.getEmployeeName();
		if(employee.getDepartment() != null) {
			this.departmentName = employee.getDepartment().getDepartmentName();			
		}
	}

	public Long getId() {
		return employeeId;
	}

	public void setId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
