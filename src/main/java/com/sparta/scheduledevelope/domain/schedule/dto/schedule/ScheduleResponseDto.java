package com.sparta.scheduledevelope.domain.schedule.dto.schedule;

import com.sparta.scheduledevelope.domain.schedule.entity.Schedule;
import com.sparta.scheduledevelope.domain.schedule.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> toUserIds;     // 배정된 유저 아이디
    private String weather;     // 날씨 정보

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.commentCount = schedule.getComments().size();
        this.password = schedule.getPassword();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
        this.username = schedule.getUser().getUsername();
        this.toUserIds = schedule.getToSchedules()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        this.weather = schedule.getWeather();
    }
}
