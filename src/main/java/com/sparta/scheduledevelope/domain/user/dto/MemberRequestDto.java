package com.sparta.scheduledevelope.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberRequestDto {

	@NotBlank(message = "유저명은 필수 입력란 입니다.")
	@Size(min = 3, max = 10, message = "유저명은 3 ~ 10자 이내로 입력하세요.")
	private String membername;

	@NotBlank(message = "이메일을 입력하세요.")
	@Email(message = "잘못된 이메일 형식입니다.")
	private String email;
}
