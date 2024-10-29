package com.sparta.scheduledevelope.domain.user.dto.user;

import com.sparta.scheduledevelope.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.createDate = user.getCreateDate();
        this.updateDate = user.getUpdateDate();
    }
}
