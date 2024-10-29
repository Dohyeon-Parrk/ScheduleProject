package com.sparta.scheduledevelope.domain.user.dto.user.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "유저명을 입력하세요.")
    @Size(min = 3, max = 10, message = "유저명은 3 ~ 10자 이내로 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;
}
