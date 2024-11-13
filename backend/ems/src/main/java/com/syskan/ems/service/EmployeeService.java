package com.syskan.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syskan.ems.model.Employee;
import com.syskan.ems.repo.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public List<Employee> findAll() {
		return repository.findAll();
	}

	public Employee save(Employee employee) {
		return repository.save(employee);
	}

}
