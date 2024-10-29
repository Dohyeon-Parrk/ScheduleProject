package com.sparta.scheduledevelope.domain.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(scheduleService.createSchedule(scheduleRequestDto));
    }

    // 일정 전체 조회
    @GetMapping()
    public ResponseEntity<ScheduleResponseDto> getScheduleList() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body((ScheduleResponseDto)scheduleService.getScheduleList());
    }

    // 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(scheduleService.getSchedule(scheduleId));
    }

    // 일정 수정
    @PutMapping("/{scheduleId")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.updateSchedule(scheduleId, scheduleRequestDto);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }
}
