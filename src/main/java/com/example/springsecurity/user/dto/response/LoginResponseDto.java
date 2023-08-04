package com.example.springsecurity.user.dto.response;

import com.example.springsecurity.user.entities.Role;
import com.example.springsecurity.user.entities.User;

import lombok.Getter;

@Getter
public class LoginResponseDto {
	private final String email;
	private final Role role;
	private final String token;

	public LoginResponseDto(User user, String token) {
		this.email = user.getEmail();
		this.role = user.getRole();
		this.token = token;
	}
}
