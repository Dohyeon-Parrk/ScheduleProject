package com.sparta.scheduledevelope.domain.schedule.repository;

import com.sparta.scheduledevelope.domain.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findCommentById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다." + id));
    }
}
