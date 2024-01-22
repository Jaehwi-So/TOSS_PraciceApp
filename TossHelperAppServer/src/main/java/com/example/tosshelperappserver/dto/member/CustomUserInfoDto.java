package com.example.tosshelperappserver.dto.member;

import com.example.tosshelperappserver.common.constant.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends MemberDto{
    private Long memberId;

    private String email;

    private String name;

    private String password;

    private RoleType role;

}