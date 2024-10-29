package com.sparta.scheduledevelope.domain.user.controller;

import com.sparta.scheduledevelope.domain.user.dto.user.UserResponseDto;
import com.sparta.scheduledevelope.domain.user.dto.user.delete.UserDeleteRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.user.login.UserLoginRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.user.signup.UserSignupRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.user.update.UserUpdateRequestDto;
import com.sparta.scheduledevelope.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입(유저 생성)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto){
        String token = userService.signup(userSignupRequestDto);  // 회원 가입 후 JWT 토큰 발급
        return ResponseEntity.ok(token);    // JWT 토큰 리턴
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.login(userLoginRequestDto);
    }

    // 모든 유저 조회
    @GetMapping()
    public List<UserResponseDto> getUserList(){
        return userService.getUserList();
    }

    // 유저 조회 (단건 조회)
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    // 유저 수정
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id,
                           @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        userService.updateUser(id, userUpdateRequestDto);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id,
                           @Valid @RequestBody UserDeleteRequestDto userDeleteRequestDto){
        userService.deleteUser(id, userDeleteRequestDto);
    }

}
