package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/{scheduleId}")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto){
        return commentService.createComment(scheduleId, requestDto);
    }

    // 해당 작성자의 댓글 조회 or 전체조회
    @GetMapping("/{id}")
    public List<CommentResponseDto> getComments(@PathVariable Long id, @RequestParam(required = false) String author){
        return commentService.getComments(id, author);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @RequestParam String password){
        commentService.updateComment(id, requestDto, password);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestParam String password){
        commentService.deleteComment(id, password);
    }
}
