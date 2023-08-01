package com.otpLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otpLogin.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String username);
}
