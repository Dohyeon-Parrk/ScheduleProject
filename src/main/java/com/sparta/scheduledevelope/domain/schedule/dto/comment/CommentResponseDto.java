package com.sparta.scheduledevelope.domain.schedule.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String membername;

}
