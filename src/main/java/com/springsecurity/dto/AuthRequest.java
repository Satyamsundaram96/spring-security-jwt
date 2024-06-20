package com.springsecurity.dto;

import lombok.Data;

@Data
public class AuthRequest {
	
	public String userName;
	public String password;
	
	public AuthRequest() {
	
	}

	public AuthRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
