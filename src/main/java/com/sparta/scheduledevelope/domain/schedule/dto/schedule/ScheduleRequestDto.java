package com.sparta.scheduledevelope.domain.schedule.dto.schedule;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotNull
    private Long memberId;

    @Size(min = 2, max = 10, message = "일정 제목은 2자 이상, 10자 이하입니다.")
    private String title;

    @Size(min = 2, max = 20, message = "일정 내용은 2자 이상, 20자 이하입니다.")
    private String content;
}

