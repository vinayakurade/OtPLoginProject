package com.otpLogin.service;

import com.otpLogin.dto.UserDto;
import com.otpLogin.dto.UserLoginResponse;
import com.otpLogin.dto.VerifyUserDto;
import com.otpLogin.entity.UserEntity;

public interface UserService {

	UserEntity createUser(UserDto userdto);
	UserLoginResponse loginByUser(UserDto userdto);
	String verifyOtp(VerifyUserDto verifyUserDto);

}
