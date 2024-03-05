package com.example.tosshelperappserver.service.auth;

import com.example.tosshelperappserver.dto.member.LoginRequestDto;

public interface AuthService {

    /**
     * [로그인]
     * 로그인
     * @param LoginRequestDto
     */
    String login(LoginRequestDto dto);


}
