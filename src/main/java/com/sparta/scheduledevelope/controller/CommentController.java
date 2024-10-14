package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/{scheduleId}/comments")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto){
        return commentService.createComment(scheduleId, requestDto);
    }
}
