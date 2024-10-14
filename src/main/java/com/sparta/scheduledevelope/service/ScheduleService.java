package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.ScheduleRequestDto;
import com.sparta.scheduledevelope.dto.ScheduleResponseDto;
import com.sparta.scheduledevelope.entity.Schedule;
import com.sparta.scheduledevelope.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성
    @Transactional
    public void createSchedule(ScheduleRequestDto requestDto){
        Schedule schedule = new Schedule();
        schedule.setUsername(requestDto.getUsername());
        schedule.setPassword(requestDto.getPassword());
        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        scheduleRepository.save(schedule);
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getScheduleList(){
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponseDto::new).collect(Collectors.toList());
    }

    // 선택 일정 조회
    public ScheduleResponseDto getSchedule(Long id){
        // id로 일정 조회 & 일정 없는 exception
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID : " + id));

        // 비밀번호 exception
        if (!schedule.getPassword().equals(schedule.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

        return new ScheduleResponseDto(schedule);
    }

    // 일정 수정
    @Transactional
    public void updateSchedule(Long id, ScheduleRequestDto requestDto){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID :" + id));

        if (!schedule.getPassword().equals(schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        scheduleRepository.save(schedule);
    }

    // 일정 삭제
    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }
}






