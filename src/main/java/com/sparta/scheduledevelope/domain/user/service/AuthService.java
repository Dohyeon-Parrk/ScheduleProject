package com.sparta.scheduledevelope.domain.user.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

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

	private void validateEmail(String email) {
		if(!StringUtils.hasText(email) || !email.contains("@")){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다.");
		}
	}

	@Transactional
	public void signUp(AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse) {
		validateEmail(authRequestDto.getEmail());

		Optional<Member> checkMember = memberRepository.findByEmail(authRequestDto.getEmail());

		if(checkMember.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "중복된 email 입니다.");
		}

		UserRoleEnum role = UserRoleEnum.getRole(authRequestDto.isAdmin());
		Member signUpMember = Member.from(authRequestDto, role);
		Member savedUser = memberRepository.save(signUpMember);

		String token = jwtUtil.createToken(savedUser.getEmail(), savedUser.getRole());
		jwtUtil.addJwtToCookie(token, httpServletResponse);
	}

	public void login(AuthRequestDto authRequestDto, HttpServletResponse response) {
		Member checkMember = memberRepository.findByEmail(authRequestDto.getEmail())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."));

		checkMember.validatePassword(authRequestDto.getPassword(), passwordEncoder);

		String token = jwtUtil.createToken(checkMember.getEmail(), checkMember.getRole());
		jwtUtil.addJwtToCookie(token, response);
	}
}
