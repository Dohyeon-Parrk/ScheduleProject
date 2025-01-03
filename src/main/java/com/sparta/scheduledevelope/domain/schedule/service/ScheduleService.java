package com.sparta.scheduledevelope.domain.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponsePage;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.schedule.repository.ScheduleRepository;
import com.sparta.scheduledevelope.domain.user.entity.Member;
import com.sparta.scheduledevelope.domain.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Member member = memberRepository.findById(scheduleRequestDto.getMemberId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다." + scheduleRequestDto.getMemberId()));

        Schedule schedule = scheduleRepository.save(Schedule.from(scheduleRequestDto, member));

        return schedule.to();
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getScheduleList() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream()
            .map(Schedule::to)
            .collect(Collectors.toList());
    }

    // 일정 단건 조회
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));

        return schedule.to();
    }

    // 일정 수정
    @Transactional
    public void updateSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));
        schedule.updateDate(scheduleRequestDto);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));
        scheduleRepository.delete(schedule);
    }

    // 페이징
    public ScheduleResponsePage getScheduleListPaging(int page, int size, String criteria){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        return new ScheduleResponsePage(schedules);
    }

    // 일정에 유저 배정
    @Transactional
    public void assignMember(Long memberId, Long scheduleId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다. : " + memberId));
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));
        schedule.addMember(member);
    }
}





