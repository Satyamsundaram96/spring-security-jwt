package com.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.dto.AuthRequest;
import com.springsecurity.entity.Employee;
import com.springsecurity.service.EmployeeService;
import com.springsecurity.service.JWTService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	public static final String AUTHORITY_ROLE_HR = "hasAuthority('ROLE_HR')";
	
	public static final String AUTHORITY_ROLE_EMPLOYEE = "hasAuthority('ROLE_EMPLOYEE')";
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private EmployeeService service;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Spring Security !! Your official credential already has been shared over email...";
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUserName());
		} else {
			throw new UsernameNotFoundException("Sorry Authentication failed and u need to authenticate !!");
		}
	}
	
	@PostMapping("/create")
	public Employee onBoardNewEmployee(@RequestBody Employee employee) {
		return service.createNewEmployee(employee);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_HR') or hasAuthority('ROLE_MANAGER')")
	public List<Employee> getAll() {
		return service.getEmployees();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize(AUTHORITY_ROLE_EMPLOYEE)
	public Employee getEmployeeById(@PathVariable Integer id) {
		return service.getEmployee(id);
	}
	
	@PutMapping("/update")
	@PreAuthorize(AUTHORITY_ROLE_HR)
	public Employee updateRoles(@RequestBody Employee employee) {
		return service.updateRoleOfEmployee(employee);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(AUTHORITY_ROLE_HR)
	public String deleteEmployee(@PathVariable Integer id) {
		return service.deleteEmployeeById(id);
	}

}
