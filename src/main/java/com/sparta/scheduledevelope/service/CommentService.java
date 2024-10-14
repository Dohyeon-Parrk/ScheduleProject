package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.entity.Comment;
import com.sparta.scheduledevelope.entity.Schedule;
import com.sparta.scheduledevelope.repository.CommentRepository;
import com.sparta.scheduledevelope.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;


    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다 : " + scheduleId));

        Comment comment = new Comment();
        comment.setAuthor(requestDto.getAuthor());
        comment.setComment(requestDto.getComment());
        comment.setSchedule(schedule);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
