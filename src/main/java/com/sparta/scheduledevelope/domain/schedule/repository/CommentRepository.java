package com.sparta.scheduledevelope.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.scheduledevelope.domain.schedule.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
