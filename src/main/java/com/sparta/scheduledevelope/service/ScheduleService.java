package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void createSchedule(){

    }

    public void getScheduleList(){

    }

    public void getSchedule(Long id){

    }

    public void updateSchedule(Long id){

    }

    public void deleteSchedule(Long id){

    }
}
