package com.sparta.scheduledevelope.repository;

import com.sparta.scheduledevelope.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 리스트 조회
    List<User> findByIdIn(List<Long> userIds);
}
