package com.example.springsecurity.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.user.dto.request.LoginRequestDto;
import com.example.springsecurity.user.dto.response.LoginResponseDto;
import com.example.springsecurity.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LoginController {
	private final UserService userService;

	@PostMapping("/login")
	public LoginResponseDto loginUser(@RequestBody LoginRequestDto req) {
		return userService.loginUser(req);
	}
}
