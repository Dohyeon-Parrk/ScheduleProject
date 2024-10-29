package com.sparta.scheduledevelope.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.scheduledevelope.common.auth.JwtUtil;
import com.sparta.scheduledevelope.common.auth.PasswordEncoder;
import com.sparta.scheduledevelope.domain.user.dto.auth.AuthRequestDto;
import com.sparta.scheduledevelope.domain.user.entity.Member;
import com.sparta.scheduledevelope.domain.user.entity.UserRoleEnum;
import com.sparta.scheduledevelope.domain.user.repository.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Transactional
	public void signUp(AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse) {

		authRequestDto.initPassword(passwordEncoder.encode(authRequestDto.getPassword()));
		Optional<Member> checkMember = memberRepository.findByEmail(authRequestDto.getEmail());

		if(!checkMember.isEmpty()){
			throw new IllegalArgumentException("중복된 email 입니다.");
		}

		UserRoleEnum role = UserRoleEnum.getRole(authRequestDto.isAdmin());
		Member signUpMember = Member.from(authRequestDto, role);

		Member savedUser = memberRepository.save(signUpMember);

		String token = jwtUtil.createToken(savedUser.getEmail(), savedUser.getRole());
		jwtUtil.addJwtToCookie(token, httpServletResponse);
	}

	public void login(AuthRequestDto requestDto, HttpServletResponse response) {
		Member checkMember = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

		if(!passwordEncoder.matches(requestDto.getPassword(), checkMember.getPassword())){
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		String token = jwtUtil.createToken(checkMember.getEmail(), checkMember.getRole());
		jwtUtil.addJwtToCookie(token, response);
	}
}
