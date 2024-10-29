package com.sparta.scheduledevelope.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.user.dto.auth.AuthRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.member.MemberRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.member.MemberResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String membername;

	@Column
	private String email;

	@Column
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	@ManyToMany(mappedBy = "memberList")
	private List<Schedule> scheduleList = new ArrayList<>();

	public static Member from(MemberRequestDto memberRequestDto) {
		Member member = new Member();
		member.initDate(memberRequestDto);
		return member;
	}

	private void initDate(MemberRequestDto memberRequestDto) {
		this.membername = memberRequestDto.getMembername();
		this.email = memberRequestDto.getEmail();
	}

	public static Member from(AuthRequestDto authRequestDto, UserRoleEnum role){
		Member member = new Member();
		member.initData(authRequestDto, role);
		return member;
	}

	private void initData(AuthRequestDto requestDto, UserRoleEnum role) {
		this.membername = requestDto.getMembername();
		this.email = requestDto.getEmail();
		this.password = requestDto.getPassword();
		this.role = role;
	}

	public MemberResponseDto to(){
		return new MemberResponseDto(
			this.id,
			this.membername,
			this.email
		);
	}

	public void updateDate(MemberRequestDto memberRequestDto) {
		this.membername = memberRequestDto.getMembername();
		this.email = memberRequestDto.getEmail();
	}

	public boolean isUser(){
		return this.role == UserRoleEnum.USER;
	}
}
