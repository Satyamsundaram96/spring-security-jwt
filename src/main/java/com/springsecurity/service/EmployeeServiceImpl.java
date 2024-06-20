package com.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.entity.Employee;
import com.springsecurity.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	public static final String DEFAULT_ROLE = "ROLE_EMPLOYEE";
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee createNewEmployee(Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRoles(DEFAULT_ROLE);
		return repository.save(employee);
	}

	@Override
	public List<Employee> getEmployees() {
		return repository.findAll();
	}

	@Override
	public Employee getEmployee(Integer id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee doesn't found."));
	}

	@Override
	public Employee updateRoleOfEmployee(Employee employee) {
		Employee existingEmployee = getEmployee(employee.getId());
		existingEmployee.setRoles(employee.getRoles());
		return repository.save(existingEmployee);
	}

	@Override
	public String deleteEmployeeById(Integer id) {
		repository.deleteById(id);
		return id + " id record deleted successfully";
	}

}
