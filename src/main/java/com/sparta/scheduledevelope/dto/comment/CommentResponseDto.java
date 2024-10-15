package com.sparta.scheduledevelope.dto.comment;

import com.sparta.scheduledevelope.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String author;
    private String password;
    private String comment;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.password = comment.getPassword();
        this.comment = comment.getComment();
    }
}
