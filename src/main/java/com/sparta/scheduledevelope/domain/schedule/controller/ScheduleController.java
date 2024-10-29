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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponsePage;
import com.sparta.scheduledevelope.domain.schedule.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(scheduleService.createSchedule(scheduleRequestDto));
    }

    // 일정 전체 조회
    @GetMapping()
    public ResponseEntity<ScheduleResponsePage> getScheduleList(@RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "modifiedAt") String criteria) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(scheduleService.getScheduleListPaging(page, size, criteria));
    }

    // 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(scheduleService.getSchedule(scheduleId));
    }

    // 일정 수정
    @PutMapping("/{scheduleId}")
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

    // 일정에 유저 배정
    @PostMapping("/{scheduleId}/assign/{memberId}")
    public ResponseEntity<Void> assignUserToSchedule(@PathVariable Long memberId, @PathVariable Long scheduleId) {
        scheduleService.assignMember(memberId, scheduleId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }
}
