package com.example.tosshelperappserver.service.member;

import com.example.tosshelperappserver.dto.member.CustomUserInfoDto;
import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;

public interface MemberService {

    /**
     * [유저 추가]
     * 유저 신규 추가
     * @param MemberJoinRequestDto
     */
    Long addMember(MemberJoinRequestDto member);

    /**
     * [유저 추가]
     * 유저 신규 추가
     * @param MemberJoinRequestDto
     */
    CustomUserInfoDto getMemberInfoIncludePwdByEmail(String email);




}