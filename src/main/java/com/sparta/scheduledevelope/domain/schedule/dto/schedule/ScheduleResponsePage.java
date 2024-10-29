package com.sparta.scheduledevelope.domain.schedule.dto.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;

import lombok.Getter;

@Getter
public class ScheduleResponsePage {

	private final List<ScheduleResponseDto> schedules;
	private final int totalPages;
	private final long totalElements;

	public ScheduleResponsePage(Page<Schedule> page){
		this.schedules = page.getContent().stream()
			.map(Schedule::to)
			.collect(Collectors.toList());
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
	}
}
