package com.sparta.scheduledevelope.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.scheduledevelope.domain.user.dto.MemberRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.MemberResponseDto;
import com.sparta.scheduledevelope.domain.user.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	// 유저 생성
	@PostMapping()
	public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(memberService.createMember(memberRequestDto));
	}

	// 유저 조회
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(memberService.getMember(memberId));
	}

	// 유저 수정
	@PutMapping("/{memberId}")
	public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody MemberRequestDto memberRequestDto) {
		memberService.updateMember(memberRequestDto, memberId);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	// 유저 삭제
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
		memberService.deleteMember(memberId);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}
}
