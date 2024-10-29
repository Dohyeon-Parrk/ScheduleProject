package com.sparta.scheduledevelope.domain.schedule.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "유저명을 입력하세요.")
    @Size(max = 10, message = "유저명은 최대 10자 이내로 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(max = 6, message = "비밀번호는 최대 6자 이내로 입력하세요.")
    private String password;

    @NotBlank(message = "댓글 내용을 입력하세요.")
    @Size(max = 50)
    private String comment;

}

