package com.sparta.scheduledevelope.domain.schedule.repository;

import com.sparta.scheduledevelope.domain.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 리스트 조회
    List<User> findByIdIn(List<Long> userIds);

    // 이메일 조회
    Optional<User> findByEmail(String email);

    // 유저네임 조회
    Optional<Object> findByUsername(String username);
}
