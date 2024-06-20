package com.springsecurity.config;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.entity.Employee;
import com.springsecurity.repository.EmployeeRepository;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository empRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Employee> employee = empRepository.findByUserName(username);
		return employee.map(EmployeeUserDetails::new)
				       .orElseThrow(() -> new UsernameNotFoundException(username + " not found in system"));
	}

}
