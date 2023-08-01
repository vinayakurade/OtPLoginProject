package com.otpLogin.dto;

import lombok.Data;

@Data
public class VerifyUserDto {
	private String username;
	private String otp;
}
