package com.example.tosshelperappserver.service;

import com.example.tosshelperappserver.dto.member.swagger.LoginRequestDto;

public interface AuthService {

    /**
     * [로그인]
     * 로그인
     * @param LoginRequestDto
     */
    String login(LoginRequestDto dto);


}
