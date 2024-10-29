package com.sparta.scheduledevelope.domain.schedule.entity;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public static Comment from(CommentRequestDto commentRequestDto, Schedule schedule) {
        Comment comment = new Comment();
        comment.initDate(commentRequestDto, schedule);
        return comment;
    }

    private void initDate(CommentRequestDto commentRequestDto, Schedule schedule) {
        this.comment = commentRequestDto.getComment();
        this.username = commentRequestDto.getUsername();
        this.schedule = schedule;
    }

    public void updateDate(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
        this.username = commentRequestDto.getUsername();
    }

    public CommentResponseDto to() {
        return new CommentResponseDto(
            this.id,
            this.comment,
            this.username
        );
    }
}