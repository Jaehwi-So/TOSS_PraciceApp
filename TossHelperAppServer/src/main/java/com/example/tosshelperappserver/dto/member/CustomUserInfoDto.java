package com.example.tosshelperappserver.dto.member;

import com.example.tosshelperappserver.common.constant.RoleType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends MemberDto{

    private String password;

    private RoleType role;

}