package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.comment.CommentRequestDto;
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
    public void createComment(Long scheduleId, CommentRequestDto requestDto) {

    }
}
