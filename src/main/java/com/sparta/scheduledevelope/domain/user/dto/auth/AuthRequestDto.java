package com.sparta.scheduledevelope.domain.user.dto.auth;

import lombok.Getter;

@Getter
public class AuthRequestDto {

	private String email;
	private String membername;
	private String password;
	private boolean admin = false;

	public void initPassword(String password) {
		this.password = password;
	}
}
