package com.sparta.scheduledevelope.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDto {
//    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(max = 6, message = "비밀번호는 최대 6자 이내로 입력하세요.")
    private String password;

    @NotBlank(message = "일정의 제목을 입력하세요.")
    @Size(max = 10, message = "제목은 최대 10자 이내로 입력하세요.")
    private String title;

    @NotBlank(message = "일정의 내용을 입력하세요.")
    @Size(max = 255)
    private String content;
}
