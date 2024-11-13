package com.syskan.ems.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syskan.ems.model.Employee;
import com.syskan.ems.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return service.findAll();
	}

	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return service.save(employee);
	}

}