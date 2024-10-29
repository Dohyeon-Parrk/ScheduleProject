package com.sparta.scheduledevelope.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.scheduledevelope.domain.user.dto.MemberRequestDto;
import com.sparta.scheduledevelope.domain.user.dto.MemberResponseDto;
import com.sparta.scheduledevelope.domain.user.entity.Member;
import com.sparta.scheduledevelope.domain.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 유저 생성
	@Transactional
	public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
		Member member = Member.from(memberRequestDto);
		Member savedMember = memberRepository.save(member);

		return savedMember.to();
	}

	// 유저 조회
	public MemberResponseDto getMember(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다." + memberId));

		return member.to();
	}

	// 유저 수정
	@Transactional
	public void updateMember(MemberRequestDto memberRequestDto, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다." + memberId));

		member.updateDate(memberRequestDto);
	}

	// 유저 삭제
	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다." + memberId));

		memberRepository.deleteById(memberId);
	}
}
