package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    // 일정 생성
    @PostMapping()
    public void createSchedule(){

    }

    // 전체 일정 조회
    @GetMapping()
    public void getScheduleList(){

    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public void getschedule(@PathVariable Long id){

    }

    // 일정 수정
    @PutMapping("/{id}")
    public void updateSchedule(@PathVariable Long id){

    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id){

    }
}
