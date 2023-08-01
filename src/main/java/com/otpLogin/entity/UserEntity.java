package com.otpLogin.entity;

import jakarta.persistence.Column;
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
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "otpId", referencedColumnName = "otpId")
//	private Otp otp;

}
