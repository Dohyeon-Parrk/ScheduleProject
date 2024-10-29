package com.sparta.scheduledevelope.domain.schedule.controller;

import com.sparta.scheduledevelope.domain.schedule.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // ADMIN 권한 부여
    @PutMapping("/user/role/{id}")
    public String changeUserRole(@PathVariable Long id){
        userService.changeUserRole(id);
        return "관리자 권한으로 변경되었습니다." + id;
    }
}
