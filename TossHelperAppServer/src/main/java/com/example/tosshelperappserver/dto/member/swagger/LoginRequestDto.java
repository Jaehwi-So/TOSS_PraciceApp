package com.example.tosshelperappserver.dto.member.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AUTH_REQ_01 : 로그인 요청 DTO")
public class LoginRequestDto {

    @NotNull(message = "이메일 입력은 필수입니다.")
    @Email
    private String email;


    @NotNull(message = "패스워드 입력은 필수입니다.")
    private String password;
}
