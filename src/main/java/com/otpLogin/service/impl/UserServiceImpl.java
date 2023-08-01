package com.otpLogin.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.otpLogin.Exception.InvalidCredentialException;
import com.otpLogin.dto.UserDto;
import com.otpLogin.dto.UserLoginResponse;
import com.otpLogin.dto.VerifyUserDto;
import com.otpLogin.entity.OtpEntity;
import com.otpLogin.entity.UserEntity;
import com.otpLogin.repository.OtpRepository;
import com.otpLogin.repository.UserRepository;
import com.otpLogin.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final OtpRepository otpRepository;

	public UserServiceImpl(UserRepository userRepository, OtpRepository otpRepository) {
		this.userRepository = userRepository;
		this.otpRepository = otpRepository;
	}

	@Override
	public UserEntity createUser(UserDto userdto) {
		UserEntity user = new UserEntity();
		user.setUsername(userdto.getUsername());
		user.setPassword(userdto.getPassword());
		UserEntity createdUser = userRepository.save(user);
		return createdUser;
	}

	@Override
	public UserLoginResponse loginByUser(UserDto userdto) {
		UserEntity user = userRepository.findByUsername(userdto.getUsername())
				.orElseThrow(() -> new InvalidCredentialException());

		if (user.getPassword().equals(userdto.getPassword())) {
			String generatedOtp = getRandomNumberString();

			OtpEntity otp = new OtpEntity();
			otp.setOtp(generatedOtp);
			otp.setUserId(user.getUserId());
			otp.setStatus("CREATED");

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expiry = now.plusMinutes(5);
			otp.setCreatedAt(now);
			otp.setExpiryAt(expiry);
			otpRepository.save(otp);

			UserLoginResponse userLoginRespons = new UserLoginResponse();
			userLoginRespons.setOtp(generatedOtp);
			return userLoginRespons;

		} else {
			throw new InvalidCredentialException("Invalid Credentials");
		}

	}

	public String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
		// this will convert any number sequence into 6 character.

	}

	@Override
	public String verifyOtp(VerifyUserDto verifyUserDto) {
		UserEntity user = userRepository.findByUsername(verifyUserDto.getUsername()).orElseThrow(
				() -> new InvalidCredentialException("User with " + verifyUserDto.getUsername() + " not exist"));

		OtpEntity otpOfuser = otpRepository.findFirstByUserIdOrderByCreatedAtDesc(user.getUserId()).orElseThrow(
				() -> new InvalidCredentialException("Otp not found for user " + verifyUserDto.getUsername()));

		if (otpOfuser.getOtp().equals(verifyUserDto.getOtp())) {
			if (otpOfuser.getExpiryAt().isAfter(LocalDateTime.now())) {
				if(!otpOfuser.getStatus().equals("USED")) {
					otpOfuser.setStatus("USED");
					otpRepository.save(otpOfuser);
					return "SUCCESS";
				}else {
					log.info("Givne Otp is used");
					throw new InvalidCredentialException("Givne Otp is used");
				}
				
			} else {
				log.info("Givne Otp is expired at{}", otpOfuser.getExpiryAt());
				throw new InvalidCredentialException("Givne Otp is expired");
			}

		} else {
			throw new InvalidCredentialException("Wrong OTP");
		}

	}

}
