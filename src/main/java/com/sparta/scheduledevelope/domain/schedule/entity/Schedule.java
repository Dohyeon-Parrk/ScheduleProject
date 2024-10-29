package com.sparta.scheduledevelope.domain.schedule.entity;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Schedule extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String title;

    @Column
    private String content;

    public static Schedule from(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.initData(scheduleRequestDto);
        return schedule;
    }

    private void initData(ScheduleRequestDto scheduleRequestDto) {
        this.username = scheduleRequestDto.getUsername();
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
    }

    public void updateDate(ScheduleRequestDto scheduleRequestDto) {
        this.username = scheduleRequestDto.getUsername();
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
    }

    public ScheduleResponseDto to() {
        return new ScheduleResponseDto(
            id,
            username,
            title,
            content,
            getCreatedAt(),
            getModifiedAt()
        );
    }
}