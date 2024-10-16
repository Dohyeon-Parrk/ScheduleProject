package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.user.UserRequestDto;
import com.sparta.scheduledevelope.dto.user.UserResponseDto;
import com.sparta.scheduledevelope.entity.User;
import com.sparta.scheduledevelope.repository.UserRepository;
import com.sparta.scheduledevelope.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원 가입(유저 생성)
    public String signup(UserRequestDto requestDto){
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(requestDto.getEmail());

        userRepository.save(user);

        log.info("회원 가입되었습니다. : " + user.getUsername());

        // JWT 토큰 발급
        return jwtUtil.createToken(user.getUsername());
    }

    // 전체 유저 조회
    public List<UserResponseDto> getUserList() {
        List<User> users = userRepository.findAll();

        log.info("현재 등록된 전체 유저 : " + userRepository.findAll().size() + " 명");

        return users.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    // 유저 단건 조회
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. " + id));

        log.info("선택한 유저 : " + user.getUsername());

        return new UserResponseDto(user);
    }

    // 유저 수정
    @Transactional
    public void updateUser(Long id, UserRequestDto requestDto, String inputPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. " + id));

        if(!passwordEncoder.matches(inputPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }

        user.setUsername(requestDto.getUsername());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(encodedPassword);

        user.setEmail(requestDto.getEmail());

        userRepository.save(user);

        log.info("유저 정보가 수정되었습니다. : " + user.getUsername());
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id, String inputPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. : " + id));

        if(!passwordEncoder.matches(inputPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(id);

        log.info("유저 정보가 삭제되었습니다. : " + user.getUsername());
    }
}
