package com.sparta.scheduledevelope.dto.schedule;

import com.sparta.scheduledevelope.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private int commentCount;   // 댓글 개수
    private String username;        // 유저네임
    private String password;        // 비밀번호
    private String title;       // 제목
    private String content;     // 내용
    private LocalDateTime createDate;    // 작성일자
    private LocalDateTime updateDate;    // 수정일자

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.commentCount = schedule.getComments().size();
        this.username = schedule.getUsername();
        this.password = schedule.getPassword();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
