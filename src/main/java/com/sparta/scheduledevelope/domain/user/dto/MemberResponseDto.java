package com.sparta.scheduledevelope.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

	private Long id;
	private String membername;
	private String email;
}
