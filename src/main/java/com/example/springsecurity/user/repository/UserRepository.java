package com.example.springsecurity.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurity.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByEmail(String email);
}
