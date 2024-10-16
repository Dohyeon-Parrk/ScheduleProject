package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.dto.schedule.scheduleto.ScheduleToUserRequestDto;
import com.sparta.scheduledevelope.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping("/{userId}")
    public ScheduleResponseDto createSchedule(@PathVariable Long userId, @Valid @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(userId, requestDto);
    }

    // 전체 일정 조회
    @GetMapping()
    public List<ScheduleResponseDto> getScheduleList() {
        return scheduleService.getScheduleList();
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id,
                               @Valid @RequestBody ScheduleRequestDto requestDto,
                               @RequestParam String password) {
        scheduleService.updateSchedule(id, requestDto, password);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id,
                               @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
    }

    // 일정 페이징 조회
    @GetMapping("/page")
    public Page<ScheduleResponseDto> getSchedulePage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        return scheduleService.getSchedulePage(pageable);
    }

    // 유저 배정
    @PostMapping("/tousers/{scheduleId}")
    public ScheduleResponseDto userToSchedule(@PathVariable Long scheduleId,
                                              @RequestBody ScheduleToUserRequestDto requestDto){
        return scheduleService.userToSchedule(scheduleId, requestDto);
    }

    // 일정 및 배정 유저 조회
    @GetMapping("/tousers/{scheduleId}")
    public ScheduleResponseDto getUserSchedule(@PathVariable Long scheduleId){
        return scheduleService.getUserSchedule(scheduleId);
    }
}
