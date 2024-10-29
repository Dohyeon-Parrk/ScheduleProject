package com.sparta.scheduledevelope.domain.schedule.service;

import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.scheduledevelope.domain.schedule.dto.schedule.scheduleto.ScheduleToUserRequestDto;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.user.entity.User;
import com.sparta.scheduledevelope.domain.schedule.repository.ScheduleRepository;
import com.sparta.scheduledevelope.domain.user.repository.UserRepository;
import com.sparta.scheduledevelope.external.api.weather.WeatherService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;
    private final WeatherService weatherService;

    public ScheduleService(ScheduleRepository scheduleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, WeatherService weatherService) {
        this.scheduleRepository = scheduleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.weatherService = weatherService;
    }

    // 일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. " + userId));

//        schedule.setUsername(requestDto.getUsername());

        if(requestDto.getPassword().length() > 10){
            throw new IllegalArgumentException("일정 비밀번호는 최대 10자 이내여야 합니다.");
        }

        // 날씨 정보 조회
        String todayWeather = weatherService.getTodayWeather();

        Schedule schedule = new Schedule();

        // 비밀번호 암호화
        String encodedPassword  = passwordEncoder.encode(requestDto.getPassword());
        schedule.setPassword(encodedPassword);

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());
        schedule.setWeather(todayWeather);

        schedule.setUser(user);

        scheduleRepository.save(schedule);

        log.info("일정이 생성되었습니다 : " + schedule.getTitle());

        return new ScheduleResponseDto(schedule);
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
    public void updateSchedule(Long id, ScheduleRequestDto requestDto){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID :" + id));

        if (!passwordEncoder.matches(requestDto.getPassword(), schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

//        schedule.setUsername(requestDto.getUsername());

        String encodedPassword  = passwordEncoder.encode(requestDto.getPassword());
        schedule.setPassword(encodedPassword);

        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());

        scheduleRepository.save(schedule);

        log.info("일정이 수정되었습니다 : " + schedule.getTitle());
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto){
        Schedule schedule = scheduleRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. ID :" + id));

        if(!passwordEncoder.matches(scheduleRequestDto.getPassword(), schedule.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. password : ");
        }

        scheduleRepository.deleteById(id);

        log.info("일정이 삭제되었습니다 : " + schedule.getTitle());
    }

    // 일정 페이징 조회
    public Page<ScheduleResponseDto> getSchedulePage(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(ScheduleResponseDto::new);
    }

    // 유저 배정
    @Transactional
    public ScheduleResponseDto userToSchedule(Long scheduleId, ScheduleToUserRequestDto requestDto) {

        // 일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));

        // id 로 유저 ㅈ회
        List<User> users = userRepository.findByIdIn(requestDto.getUserIds());

        // 일정에 유저 배정
        for (User user : users) {
            schedule.getToSchedules().add(user);
            user.getToUser().add(schedule);
        }

        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    // 배정된 유저 목록 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto getUserSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("해당 일정이 없습니다."));

        return new ScheduleResponseDto(schedule);
    }
}






