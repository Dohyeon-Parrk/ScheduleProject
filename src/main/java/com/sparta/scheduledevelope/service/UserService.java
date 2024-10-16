package com.sparta.scheduledevelope.service;

import com.sparta.scheduledevelope.dto.user.UserResponseDto;
import com.sparta.scheduledevelope.dto.user.delete.UserDeleteRequestDto;
import com.sparta.scheduledevelope.dto.user.login.UserLoginRequestDto;
import com.sparta.scheduledevelope.dto.user.signup.UserSignupRequestDto;
import com.sparta.scheduledevelope.dto.user.update.UserUpdateRequestDto;
import com.sparta.scheduledevelope.entity.Schedule;
import com.sparta.scheduledevelope.entity.User;
import com.sparta.scheduledevelope.entity.UserRoleEnum;
import com.sparta.scheduledevelope.repository.UserRepository;
import com.sparta.scheduledevelope.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String signup(@Valid UserSignupRequestDto userSignupRequestDto) {
        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());

        User user = new User();

        user.setUsername(userSignupRequestDto.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userSignupRequestDto.getEmail());

        user.setRole(UserRoleEnum.USER);    // 기본 권한 USER

        userRepository.save(user);

        log.info("회원 가입되었습니다. : " + user.getUsername());

        // JWT 토큰 발급
        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    // 로그인, Jwt 발급
    public ResponseEntity<String> login(@Valid UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다. : " + userLoginRequestDto.getEmail()));

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        log.info("로그인 성공 : " + user.getEmail());

        // 로그인 성공 후, Jwt 발급
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(token);
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
    public void updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. " + id));

        if (!passwordEncoder.matches(userUpdateRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }

        // 수정 된 비밀번호 암ㅎ화
        String encodedUpdatedPassword = passwordEncoder.encode(userUpdateRequestDto.getUpdatedPassword());

        user.setUsername(userUpdateRequestDto.getUsername());
        user.setPassword(encodedUpdatedPassword);
        user.setEmail(userUpdateRequestDto.getEmail());

        userRepository.save(user);

        log.info("유저 정보가 수정되었습니다. : " + user.getUsername());
    }

    // 유저 삭제
    public void deleteUser(Long id, UserDeleteRequestDto userDeleteRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. : " + id));

        if (!passwordEncoder.matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 삭제 시, 배정된 일정에 유저가 포함 되어 있다면 해당 유저 정보도 삭제
        for (Schedule schedule : user.getToUser()) {
            schedule.getToSchedules().remove(user);
        }

        userRepository.delete(user);

        log.info("유저 정보가 삭제되었습니다. : " + user.getUsername());
    }

    // ADMIN 권한 부여
    public void changeUserRole(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. : " + id));

        user.setRole(UserRoleEnum.ADMIN);
        userRepository.save(user);
    }
}
