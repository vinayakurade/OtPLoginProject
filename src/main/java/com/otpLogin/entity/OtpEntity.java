package com.otpLogin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "otp")
public class OtpEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long otpId;
	private String otp;
	private Long userId;
	private LocalDateTime createdAt;
	private LocalDateTime expiryAt;

	private String status;

}
