package com.sparta.scheduledevelope.domain.schedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.domain.schedule.entity.Comment;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.schedule.repository.CommentRepository;
import com.sparta.scheduledevelope.domain.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long scheduleId){
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        Comment comment = Comment.from(commentRequestDto, schedule);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.to();
    }

    // 댓글 수정
    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long scheduleId, Long commentId){
        scheduleRepository.findScheduleById(scheduleId);
        Comment comment = commentRepository.findCommentById(commentId);
        comment.updateDate(commentRequestDto);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId){
        scheduleRepository.findScheduleById(scheduleId);
        commentRepository.findCommentById(commentId);
        commentRepository.deleteById(commentId);
    }
}
