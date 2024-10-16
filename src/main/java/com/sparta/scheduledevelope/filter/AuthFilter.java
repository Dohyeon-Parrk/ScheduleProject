package com.sparta.scheduledevelope.filter;

import com.sparta.scheduledevelope.entity.User;
import com.sparta.scheduledevelope.entity.UserRoleEnum;
import com.sparta.scheduledevelope.repository.UserRepository;
import com.sparta.scheduledevelope.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String url = httpServletRequest.getRequestURI();

        try {
            // 회원가입, 로그인 API는 인증 없이 통과
            if (isPublicUrl(url)) {
                chain.doFilter(request, response);
                return;
            }

            // 헤더에서 JWT 토큰 추출
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            if (!StringUtils.hasText(tokenValue)) {
                log.error("토큰이 없습니다.");
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 없습니다.");
                return;
            }

            // JWT 토큰 검증
            String token = tokenValue;
            if (!jwtUtil.validateToken(token)) {
                log.error("유효하지 않은 JWT 토큰입니다.");
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
                return;
            }

            // 토큰에서 사용자 정보 추출 및 권한 추출
            Claims userInfo = jwtUtil.getUserInfoFromToken(token);
            User user = (User) userRepository.findByUsername(userInfo.getSubject())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 일정 수정 및 삭제 권한 체크 -> ADMIN 관리자만 가능
            if (isScheduleHandleAuthorization(httpServletRequest) && user.getRole() != UserRoleEnum.ADMIN) {
                log.error("권한이 없는 사용자입니다.");
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다.");
                return;
            }

            // 사용자 정보를 요청 속성에 저장
            request.setAttribute("user", user);

            // 다음 필터로 전달
            chain.doFilter(request, response);

        } catch (Exception e) {
            log.error("인증 오류: {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 오류가 발생했습니다" + e.getMessage());
        }
    }

    // 공개 API 경로 체크
    private boolean isPublicUrl(String url) {
        return url.startsWith("/api/user/signup") || url.startsWith("/api/user/login");
    }

    // 일정 수정 및 삭제 권한이 필요한 .URL
    private boolean isScheduleHandleAuthorization(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();

        // 일정 수정 -> PUT /api/schedule/{id}
        // 일정 삭제 -> DELETE /api/schedule/{id}
        return (url.startsWith("/api/schedule/") && (method.equals("PUT") || method.equals("DELETE")));
    }
}
