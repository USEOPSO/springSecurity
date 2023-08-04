package com.example.springsecurity.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity.global.jwt.TokenProvider;
import com.example.springsecurity.user.dto.request.LoginRequestDto;
import com.example.springsecurity.user.dto.request.RegisterUserRequestDto;
import com.example.springsecurity.user.dto.request.UpdateRequestDto;
import com.example.springsecurity.user.dto.response.LoginResponseDto;
import com.example.springsecurity.user.dto.response.RegisterUserResponseDto;
import com.example.springsecurity.user.entities.User;
import com.example.springsecurity.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public RegisterUserResponseDto getUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
		return new RegisterUserResponseDto(user);
	}

	public List<RegisterUserResponseDto> listableUsers() {
		return userRepository.findAll().stream().map(RegisterUserResponseDto::new).toList();
	}

	public RegisterUserResponseDto registerUser(RegisterUserRequestDto req) {
		RegisterUserRequestDto instUser = RegisterUserRequestDto.builder()
					.email(req.getEmail())
					.password(passwordEncoder.encode(req.getPassword()))
					.name(req.getName())
					.role(req.getRole())
					.build();
		User saveUser = userRepository.save(instUser.createUser());
		return new RegisterUserResponseDto(saveUser);
	}

	public RegisterUserResponseDto updateUser(Long userId, UpdateRequestDto req) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
		user.updateUser(req);
		userRepository.save(user);
		return new RegisterUserResponseDto(user);
	}

	public LoginResponseDto loginUser(LoginRequestDto req) {
		User user = userRepository.findUserByEmail(req.getEmail()).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
		String token = tokenProvider.generateJWT(user);
		return new LoginResponseDto(user, token);
	}
}
