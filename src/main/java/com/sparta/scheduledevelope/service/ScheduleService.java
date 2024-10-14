package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.entity.Schedule;
import com.sparta.scheduledevelope.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleService {

    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PasswordEncoder passwordEncoder) {
        this.scheduleRepository = scheduleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 일정 생성
    @Transactional
    public void createSchedule(ScheduleRequestDto requestDto){
        Schedule schedule = new Schedule();

        schedule.setUsername(requestDto.getUsername());

        // 비밀번호 암호화
        String encodedPassword  = passwordEncoder.encode(requestDto.getPassword());
        schedule.setPassword(encodedPassword);

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        scheduleRepository.save(schedule);

        log.info("일정이 생성되었습니다 : " + schedule.getTitle());
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getScheduleList(){
        List<Schedule> schedules = scheduleRepository.findAll();

        log.info("현재 등록된 전체 일정 : " + scheduleRepository.findAll().size() + " 개");

        return schedules.stream().map(ScheduleResponseDto::new).collect(Collectors.toList());
    }

    // 선택 일정 조회
    public ScheduleResponseDto getSchedule(Long id){
        // id로 일정 조회 & 일정 없는 exception
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID : " + id));

        log.info("선택한 일정 조회 : " + schedule.getTitle());

        return new ScheduleResponseDto(schedule);
    }

    // 일정 수정
    @Transactional
    public void updateSchedule(Long id, ScheduleRequestDto requestDto, String inputPassword){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID :" + id));

        if (!passwordEncoder.matches(inputPassword, schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

        schedule.setUsername(requestDto.getUsername());

        String encodedPassword  = passwordEncoder.encode(requestDto.getPassword());
        schedule.setPassword(encodedPassword);

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        scheduleRepository.save(schedule);

        log.info("일정이 수정되었습니다 : " + schedule.getTitle());
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id, String inputPassword){
        Schedule schedule = scheduleRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID :" + id));

        if(!passwordEncoder.matches(inputPassword, schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

        scheduleRepository.deleteById(id);

        log.info("일정이 삭제되었습니다 : " + schedule.getTitle());
    }
}






