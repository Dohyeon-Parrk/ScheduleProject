package com.sparta.scheduledevelope.domain.schedule.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.domain.schedule.entity.Comment;
import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.schedule.repository.CommentRepository;
import com.sparta.scheduledevelope.domain.schedule.repository.ScheduleRepository;
import com.sparta.scheduledevelope.domain.user.entity.Member;

import com.sparta.scheduledevelope.domain.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));

        Member member = memberRepository.findById(commentRequestDto.getMemberId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다." + commentRequestDto.getMemberId()));

        Comment comment = Comment.from(commentRequestDto, schedule, member);
        Comment savedComment = commentRepository.save(comment);

        return savedComment.to();
    }

    // 댓글 수정
    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long scheduleId, Long commentId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. : " + scheduleId));

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다. : " + commentId));

        comment.updateDate(commentRequestDto);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다. ID: " + scheduleId));

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다. ID: " + commentId));

        commentRepository.delete(comment);
    }
}
