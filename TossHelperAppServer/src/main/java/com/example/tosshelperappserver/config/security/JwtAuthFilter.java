package com.example.tosshelperappserver.config.security;

import com.example.tosshelperappserver.config.exception.custom.AuthForbiddenException;
import com.example.tosshelperappserver.config.security.user.CustomUserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 한 번 실행 보장

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    @Override
    // JWT 토큰 검증, 사용자 인증 로직 구현
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //HTTP 요청의 Header에서 Authorization 값 가져옴
        String authorizationHeader = request.getHeader("Authorization");

        //JWT가 헤더에 없을 경우
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            throw new AuthForbiddenException("JWT Not Found");
        }
        String token = authorizationHeader.substring(7);


        if (jwtUtil.validateToken(token)) {
            // Jwt Token에서 loginId 추출
            Long userId = jwtUtil.getUserId(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

            if (userDetails != null) {
                //첫번쩨 매개변수 : userDetails
                //두번째 매개변수 : 패스워드, 사용 안해서 null로
                //세번째 매개변수 : 사용자 권한 정보를 나타내는 SimpleGrantedAuthority 객체 생성, 이를 리스트로 묶어서 토큰에 설정
                //loginUser.getRole().name()은 사용자의 Role을 문자열로 가져와서 해당 역할 부여
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new EntityNotFoundException("해당하는 유저가 없습니다.");
            }
        }

        else {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.");
        }
        filterChain.doFilter(request, response);
    }
}
