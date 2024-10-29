package com.sparta.scheduledevelope.domain.schedule.controller;

import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.domain.schedule.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule/{scheduleId}/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping()
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(commentRequestDto, scheduleId));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.updateComment(commentRequestDto, scheduleId, commentId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId")
    public ResponseEntity<Void> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId){
        commentService.deleteComment(scheduleId, commentId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }
}