package com.sparta.scheduledevelope.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.user.dto.MemberRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.MemberResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
}
