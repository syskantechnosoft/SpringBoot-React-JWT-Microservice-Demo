package com.syskan.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syskan.ems.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
