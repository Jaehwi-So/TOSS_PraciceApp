package com.example.tosshelperappserver.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "MEM_RES_01 : 회원가입 응답 DTO")
public class MemberJoinResponseDto {

    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;

}