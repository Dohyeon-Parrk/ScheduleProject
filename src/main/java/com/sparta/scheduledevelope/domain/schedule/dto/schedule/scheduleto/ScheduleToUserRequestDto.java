package com.sparta.scheduledevelope.domain.schedule.dto.schedule.scheduleto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleToUserRequestDto {
    private List<Long> userIds;     // 배정할 유저 id 목록
}
