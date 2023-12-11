package com.example.userregistration.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {
	@NotBlank(message = "Username cannot be blank or null")
	private String username;
	@NotBlank(message = "Password cannot be blank or null")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[_#$%.]).*$", message = "Password must contain at least 1 number, 1 Capitalized letter, and 1 special character in this set '_#$%.'")
	private String password;
	@NotBlank(message = "IP address cannot be blank or null")
	private String ipAddress;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
