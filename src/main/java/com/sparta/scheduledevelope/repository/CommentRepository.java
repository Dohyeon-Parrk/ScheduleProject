package com.sparta.scheduledevelope.repository;

import com.sparta.scheduledevelope.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 전체 댓글 조회
    List<Comment> findByScheduleId(Long scheduleId);

    // 해당 작성자의 댓글 조회
    @Query("select c from Comment c where c.schedule.id = :scheduleId and c.author = :author")
    List<Comment> findCommentsByScheduleIdAndAuthor(@Param("scheduleId") Long scheduleId, @Param("author") String author);

}
