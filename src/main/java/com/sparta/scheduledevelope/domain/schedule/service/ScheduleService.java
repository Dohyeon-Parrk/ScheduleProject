package com.sparta.scheduledevelope.domain.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponsePage;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.save(Schedule.from(scheduleRequestDto));
        return schedule.to();
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.stream()
            .map(Schedule::to)
            .collect(Collectors.toList());
    }

    // 일정 단건 조회
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다. : " + scheduleId));

        log.info("선택한 일정 조회 : " + scheduleId);

        return schedule.to();
    }

    // 일정 수정
    @Transactional
    public void updateSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        schedule.updateDate(scheduleRequestDto);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId){
        scheduleRepository.findScheduleById(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    // 페이징
    public ScheduleResponsePage getScheduleListPaging(int page, int size, String criteria){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return new ScheduleResponsePage(schedules);
    }
}




