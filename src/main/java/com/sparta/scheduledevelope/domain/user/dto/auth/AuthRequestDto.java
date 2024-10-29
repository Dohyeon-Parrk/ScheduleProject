package com.sparta.scheduledevelope.domain.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthRequestDto {

	@NotBlank(message = "이메일은 필수 입력 사항입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;

	@NotBlank(message = "유저 이름은 필수 입력 사항입니다.")
	private String membername;

	@NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상입니다.")
	private String password;

	private boolean admin = false;

	public void initPassword(String password) {
		this.password = password;
	}
}
