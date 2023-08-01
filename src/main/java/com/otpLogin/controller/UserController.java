package com.otpLogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otpLogin.dto.UserDto;
import com.otpLogin.dto.UserLoginResponse;
import com.otpLogin.dto.VerifyUserDto;
import com.otpLogin.entity.UserEntity;
import com.otpLogin.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	
	private final UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserDto userdto) {
		return new ResponseEntity<UserEntity>(userService.createUser(userdto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserDto userdto) {
		return new ResponseEntity<UserLoginResponse>(userService.loginByUser(userdto), HttpStatus.OK);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<String> verifyOtp(@RequestBody VerifyUserDto verifyUserDto) {
		return new ResponseEntity<String>(userService.verifyOtp(verifyUserDto), HttpStatus.ACCEPTED);
	}

}
