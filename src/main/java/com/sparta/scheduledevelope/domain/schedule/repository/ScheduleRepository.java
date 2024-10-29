package com.sparta.scheduledevelope.domain.schedule.repository;

import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
