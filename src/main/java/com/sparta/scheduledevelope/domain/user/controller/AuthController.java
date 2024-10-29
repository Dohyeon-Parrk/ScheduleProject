package com.sparta.scheduledevelope.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.scheduledevelope.domain.user.dto.auth.AuthRequestDto;
import com.sparta.scheduledevelope.domain.user.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	// 회원가입
	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse httpServletREsponse){
		authService.signUp(authRequestDto, httpServletREsponse);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body("회원가입 완료");
	}

	// 로그인
	@PutMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse){
		authService.login(authRequestDto, httpServletResponse);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body("로그인 완료");
	}
}
