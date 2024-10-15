package com.sparta.scheduledevelope.controller;

import com.sparta.scheduledevelope.dto.user.UserRequestDto;
import com.sparta.scheduledevelope.dto.user.UserResponseDto;
import com.sparta.scheduledevelope.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성
    @PostMapping()
    public void createUser(@RequestBody UserRequestDto requestDto){
        userService.createUser(requestDto);
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
                           @RequestBody UserRequestDto requestDto,
                           @RequestParam String password){
        userService.updateUser(id, requestDto, password);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id,
                           @RequestParam String password){
        userService.deleteUser(id, password);
    }

}
