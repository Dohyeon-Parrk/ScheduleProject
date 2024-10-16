package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.comment.CommentRequestDto;
import com.sparta.scheduledevelope.dto.comment.CommentResponseDto;
import com.sparta.scheduledevelope.entity.Comment;
import com.sparta.scheduledevelope.entity.Schedule;
import com.sparta.scheduledevelope.repository.CommentRepository;
import com.sparta.scheduledevelope.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;


    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository, PasswordEncoder passwordEncoder) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다 : " + scheduleId));

        // 댓글 비밀번호 길이 제한 (10자)
        if(requestDto.getPassword().length() > 10){
            throw new IllegalArgumentException("댓글 비밀번호는 최대 10자 이내여야 합니다.");
        }

        Comment comment = new Comment();

        comment.setUsername(requestDto.getUsername());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        comment.setPassword(encodedPassword);

        comment.setComment(requestDto.getComment());
        comment.setSchedule(schedule);

        commentRepository.save(comment);

        log.info("댓글이 생성되었습니다 : " + comment.getComment());

        return new CommentResponseDto(comment);
    }

    // 해당 작성자의 댓글 조회 or 전체조회
    public List<CommentResponseDto> getComments(Long scheduleId, String username) {
        List<Comment> comments;
        if (username != null && !username.isEmpty()) {
            // 작성자가 있을 경우 작성자의 댓글만 조회
            comments = commentRepository.findCommentsByScheduleIdAndUsername(scheduleId, username);

            log.info(username + " 님의 댓글 조회 : " + comments.size() + "개");
        } else {
            // 작성자가 없을 경우 해당 일정의 모든 댓글 조회
            comments = commentRepository.findByScheduleId(scheduleId);

            log.info(scheduleId + " 에 등록된 댓글 조회 : " + comments.size() + "개");
        }
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. " + id));

        if(!passwordEncoder.matches(requestDto.getPassword(), comment.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }

        // 비밀번호 암호화 수정
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        comment.setPassword(encodedPassword);

        // 댓글 내용 수정
        comment.setComment(requestDto.getComment());

        commentRepository.save(comment);

        log.info("댓글이 수정되었습니다 : " + comment.getComment());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, String inputPassword) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다 " + id));

        if(!passwordEncoder.matches(inputPassword, comment.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.deleteById(id);

        log.info("댓글이 삭제되었습니다. : " + comment.getComment());
    }
}
