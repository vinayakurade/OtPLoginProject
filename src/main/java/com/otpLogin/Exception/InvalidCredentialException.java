package com.otpLogin.Exception;

public class InvalidCredentialException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialException() {
		super("Invalid Credentials");

	}

	public InvalidCredentialException(String message) {
		super(message);

	}

}
