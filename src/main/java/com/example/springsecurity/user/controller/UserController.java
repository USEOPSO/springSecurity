package com.example.springsecurity.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.user.dto.request.RegisterUserRequestDto;
import com.example.springsecurity.user.dto.request.UpdateRequestDto;
import com.example.springsecurity.user.dto.response.RegisterUserResponseDto;
import com.example.springsecurity.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController()
public class UserController {

	private final UserService userService;

	@GetMapping("{userId}")
	public RegisterUserResponseDto getUser(@PathVariable Long userId) {
		return userService.getUser(userId);
	}

	@GetMapping("/")
	public List<RegisterUserResponseDto> listableUsers() {
		return userService.listableUsers();
	}

	@PostMapping("/registerUser")
	public RegisterUserResponseDto registerUser(@RequestBody RegisterUserRequestDto req) {
		return userService.registerUser(req);
	}

	@PutMapping("/updateUser/{userId}")
	public RegisterUserResponseDto updateUser(@PathVariable Long userId, @RequestBody UpdateRequestDto req) {
		return userService.updateUser(userId, req);
	}

}
