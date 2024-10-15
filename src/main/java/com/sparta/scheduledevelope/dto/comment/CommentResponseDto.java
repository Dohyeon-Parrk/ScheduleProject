package com.sparta.scheduledevelope.dto.comment;

import com.sparta.scheduledevelope.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String username;
    private String password;
    private String comment;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.password = comment.getPassword();
        this.comment = comment.getComment();
    }
}
