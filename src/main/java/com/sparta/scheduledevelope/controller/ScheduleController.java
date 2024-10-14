package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.dto.ScheduleRequestDto;
import com.sparta.scheduledevelope.dto.ScheduleResponseDto;
import com.sparta.scheduledevelope.service.ScheduleService;
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
    @PostMapping()
    public void createSchedule(@RequestBody ScheduleRequestDto requestDto){
        scheduleService.createSchedule(requestDto);
    }

    // 전체 일정 조회
    @GetMapping()
    public List<ScheduleResponseDto> getScheduleList(){
        return scheduleService.getScheduleList();
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto, @RequestParam String password){
        scheduleService.updateSchedule(id, requestDto, password);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestParam String password){
        scheduleService.deleteSchedule(id, password);
    }
}
