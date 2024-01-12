package com.example.tosshelperappserver.dto.member;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Member profile join request")
public class MemberJoinRequestDto {

    @NotBlank(message = "사용자 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Schema(description = "사용자 이메일", example = "testtest@gmail.com")
    private String email;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(max = 20, message = "사용자 이름은 20글자 이하로 입력해야 합니다.")
    @Schema(description = "member name", example = "John Doe")
    private String name;

    @NotBlank
    @Schema(description = "member Password", example = "test123!")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}

