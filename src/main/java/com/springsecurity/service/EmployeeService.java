package com.springsecurity.service;

import java.util.List;

import com.springsecurity.entity.Employee;

public interface EmployeeService {
	
	Employee createNewEmployee(Employee employee);
	
	List<Employee> getEmployees();
	
	Employee getEmployee(Integer id);
	
	Employee updateRoleOfEmployee(Employee employee);
	
	String deleteEmployeeById(Integer id);

}
