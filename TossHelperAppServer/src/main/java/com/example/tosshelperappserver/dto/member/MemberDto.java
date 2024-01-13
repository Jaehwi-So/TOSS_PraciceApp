package com.example.tosshelperappserver.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "MEM_RES_02 : 회원 데이터 DTO")
public class MemberDto {

    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;

    @Schema(description = "사용자 이메일", example = "testtest@gmail.com")
    private String email;

    @Schema(description = "사용자 이름", example = "John Doe")
    private String name;

}