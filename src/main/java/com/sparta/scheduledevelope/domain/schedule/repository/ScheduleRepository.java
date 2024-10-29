package com.sparta.scheduledevelope.domain.schedule.repository;

import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	default Schedule findScheduleById(Long id){
		return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다." + id));
	}
}
