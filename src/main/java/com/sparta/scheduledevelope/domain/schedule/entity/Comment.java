package com.sparta.scheduledevelope.domain.schedule.entity;

import com.sparta.scheduledevelope.common.entity.TimeStamp;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.domain.schedule.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.domain.user.entity.Member;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public static Comment from(CommentRequestDto commentRequestDto, Schedule schedule, Member member) {
        Comment comment = new Comment();
        comment.initData(commentRequestDto, schedule, member);
        return comment;
    }

    private void initData(CommentRequestDto commentRequestDto, Schedule schedule, Member member) {
        this.comment = commentRequestDto.getComment();
        this.member = member;
        this.schedule = schedule;
    }

    public CommentResponseDto to() {
        return new CommentResponseDto(
            this.id,
            this.comment,
            this.member.getMembername()
        );
    }

    public void updateDate(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }
}
