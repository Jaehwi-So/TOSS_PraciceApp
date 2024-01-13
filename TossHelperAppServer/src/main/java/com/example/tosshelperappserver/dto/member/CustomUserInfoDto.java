package com.example.tosshelperappserver.dto.member;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends MemberDto{

    private String password;

}