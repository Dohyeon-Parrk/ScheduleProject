package com.sparta.scheduledevelope.domain.schedule.dto.schedule;

import java.time.LocalDateTime;
import java.util.List;

import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String membername;
    private String title;
    private String content;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
