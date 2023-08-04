package com.example.springsecurity.user.dto.request;

import com.example.springsecurity.user.entities.Role;
import com.example.springsecurity.user.entities.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequestDto {
	private String email;
	private String name;
	private Role role;

	@Builder
	public UpdateRequestDto(String email, String name, Role role) {
		this.email = email;
		this.name = name;
		this.role = role;
	}
}
