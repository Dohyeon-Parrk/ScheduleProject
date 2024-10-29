package com.sparta.scheduledevelope.domain.schedule.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotNull
    @Size(min = 1, max = 50, message = "댓글 내용은 50자 이하로 작성해주세요")
    private String comment;

    private Long memberId;
}

