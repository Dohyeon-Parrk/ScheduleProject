package com.sparta.scheduledevelope.domain.user.entity;

public enum UserRoleEnum {

	ADMIN("관리자"),
	USER("유저");

	private final String content;

	UserRoleEnum(String content) {
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public static UserRoleEnum getRole(boolean isAdmin){
		return isAdmin ? ADMIN :USER;
	}
}
