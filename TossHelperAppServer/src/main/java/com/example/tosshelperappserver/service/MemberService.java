package com.example.tosshelperappserver.service;

import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;

public interface MemberService {

    /**
     * [매장 사진 추가]
     * 매장에 새로운 사진을 추가한다.
     * @param cafeId 매장 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     */
    Long addMember(MemberJoinRequestDto member);
}
