package com.sparta.scheduledevelope.domain.schedule.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.user.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Schedule extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member creator;

	@Column
	private String title;

	@Column
	private String content;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "schedule",
		joinColumns = @JoinColumn(name = "schedule_id"),
		inverseJoinColumns = @JoinColumn(name = "member_id"))
	private List<Member> memberList = new ArrayList<>();

	public static Schedule from(ScheduleRequestDto scheduleRequestDto, Member member) {
		Schedule schedule = new Schedule();
		schedule.initData(scheduleRequestDto, member);
		return schedule;
	}

	private void initData(ScheduleRequestDto scheduleRequestDto, Member member) {
		this.creator = member;
		this.title = scheduleRequestDto.getTitle();
		this.content = scheduleRequestDto.getContent();
	}

	public void updateDate(ScheduleRequestDto scheduleRequestDto) {
		this.title = scheduleRequestDto.getTitle();
		this.content = scheduleRequestDto.getContent();
	}

	public ScheduleResponseDto to() {
		return new ScheduleResponseDto(
			id,
			this.creator.getMembername(),
			title,
			content,
			comments.stream().map(Comment::to).toList(),
			getCreatedAt(),
			getModifiedAt()
		);
	}

	public void addMember(Member member) {
		this.memberList.add(member);
		member.getScheduleList().add(this);
	}
}