package com.example.springsecurity.user.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springsecurity.user.entities.Role;
import com.example.springsecurity.user.entities.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterUserRequestDto {
	private String email;
	private String password;
	private String name;
	private Role role;

	@Builder
	public RegisterUserRequestDto(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public User createUser() {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.role(role)
			.build();
	}
}
