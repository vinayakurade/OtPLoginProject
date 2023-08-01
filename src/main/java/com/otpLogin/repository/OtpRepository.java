package com.otpLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otpLogin.entity.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, String>{

	Optional<OtpEntity> findFirstByUserIdOrderByCreatedAtDesc(Long userId);
	
}
