package com.example.springsecurity.user.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	USER("USER", "사용자"),
	ADMIN("ADMIN", "관리자");

	private final String key;
	private final String title;

}