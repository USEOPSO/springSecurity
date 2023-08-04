package com.example.springsecurity.user.dto.response;

import com.example.springsecurity.user.entities.Role;
import com.example.springsecurity.user.entities.User;

import lombok.Getter;

@Getter
public class RegisterUserResponseDto {
	private final Long userId;
	private final String email;
	private final String name;
	private final Role role;

	public RegisterUserResponseDto(User user) {
		this.userId = user.getUserId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole();
	}
}
