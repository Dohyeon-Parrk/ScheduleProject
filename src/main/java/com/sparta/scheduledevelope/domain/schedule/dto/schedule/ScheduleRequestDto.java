package com.sparta.scheduledevelope.domain.schedule.dto.schedule;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long memberId;
    private String title;
    private String content;
}

