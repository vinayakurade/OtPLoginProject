package com.otpLogin.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.otpLogin.Exception.InvalidCredentialException;
import com.otpLogin.dto.UserDto;
import com.otpLogin.dto.UserLoginResponse;
import com.otpLogin.dto.VerifyUserDto;
import com.otpLogin.entity.OtpEntity;
import com.otpLogin.entity.UserEntity;
import com.otpLogin.repository.OtpRepository;
import com.otpLogin.repository.UserRepository;

class UserServiceImplTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testVerifyOtpUserNotExist() {

		UserRepository userRepository = Mockito.mock(UserRepository.class);
		OtpRepository otpRepository = Mockito.mock(OtpRepository.class);

		UserServiceImpl service = new UserServiceImpl(userRepository, otpRepository);
		// Stubbping
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
		// Stubbping

		VerifyUserDto verifyUserDto = getVerifyUserDto();

		Exception exception = assertThrows(InvalidCredentialException.class, () -> service.verifyOtp(verifyUserDto));

	}

	@Test
	void testVerifyOtp() {

		UserRepository userRepository = Mockito.mock(UserRepository.class);
		OtpRepository otpRepository = Mockito.mock(OtpRepository.class);

		UserServiceImpl service = new UserServiceImpl(userRepository, otpRepository);
		// Stubbping
		UserEntity userEntity = getUserEntityObject();
		OtpEntity otpOfuser = getOtpEntity();
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userEntity));
		when(otpRepository.findFirstByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(Optional.ofNullable(otpOfuser));
		when(otpRepository.save(any(OtpEntity.class))).thenReturn(otpOfuser);
		// Stubbping

		VerifyUserDto verifyUserDto = getVerifyUserDto();

		String result = service.verifyOtp(verifyUserDto);

		assertEquals("SUCCESS", result);
	}

	@Test
	void testVerifyOtpInvalidOTP() {

		UserRepository userRepository = Mockito.mock(UserRepository.class);
		OtpRepository otpRepository = Mockito.mock(OtpRepository.class);

		UserServiceImpl service = new UserServiceImpl(userRepository, otpRepository);
		// Stubbping
		UserEntity userEntity = getUserEntityObject();
		OtpEntity otpOfuser = getOtpEntity();
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userEntity));
		when(otpRepository.findFirstByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(Optional.ofNullable(otpOfuser));
		when(otpRepository.save(any(OtpEntity.class))).thenReturn(otpOfuser);
		// Stubbping

		VerifyUserDto verifyUserDto = getVerifyUserDto();
		verifyUserDto.setOtp("xxx");

		Exception exception = assertThrows(InvalidCredentialException.class, () -> service.verifyOtp(verifyUserDto));

		assertEquals("Wrong OTP", exception.getMessage());

	}

	@Test
	void testloginByUserSuccess() {

		UserRepository userRepository = Mockito.mock(UserRepository.class);
		OtpRepository otpRepository = Mockito.mock(OtpRepository.class);

		UserServiceImpl service = new UserServiceImpl(userRepository, otpRepository);

		UserEntity userEntity = getUserEntityObject();
		when(userRepository.findByUsername("USER")).thenReturn(Optional.ofNullable(userEntity));

		UserDto userDto = getuserDto();
		UserLoginResponse response = service.loginByUser(userDto);

		assertNotNull(response);
		assertNotNull(response.getOtp());

	}

	@Test
	void testloginByUser_UserNotFound() {

		UserRepository userRepository = Mockito.mock(UserRepository.class);
		OtpRepository otpRepository = Mockito.mock(OtpRepository.class);

		UserServiceImpl service = new UserServiceImpl(userRepository, otpRepository);

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

		UserDto userDto = getuserDto();
		Exception exception = assertThrows(InvalidCredentialException.class, () -> service.loginByUser(userDto));

		assertNotNull(exception);
		assertEquals("Invalid Credentials", exception.getMessage());
	}

	private OtpEntity getOtpEntity() {

		OtpEntity otpOfuser = new OtpEntity();
		otpOfuser.setUserId(1L);
		otpOfuser.setOtp("123456");
		otpOfuser.setExpiryAt(LocalDateTime.now().plusMinutes(1L));
		otpOfuser.setStatus("CREATED");
		return otpOfuser;
	}

	private UserEntity getUserEntityObject() {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(1L);
		userEntity.setPassword("password");
		userEntity.setUsername("USER");
		return userEntity;
	}

	private VerifyUserDto getVerifyUserDto() {

		VerifyUserDto verifyUserDto = new VerifyUserDto();
		verifyUserDto.setOtp("123456");
		verifyUserDto.setUsername("USER");
		return verifyUserDto;
	}

	private UserDto getuserDto() {

		UserDto userDto = new UserDto();
		userDto.setUsername("USER");
		userDto.setPassword("password");
		return userDto;
	}

}
