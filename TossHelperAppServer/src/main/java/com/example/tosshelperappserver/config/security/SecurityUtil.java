package com.example.tosshelperappserver.config.security;

import com.example.tosshelperappserver.config.security.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {


    /**
     * [인증 유저의 PK]
     * 현재 로그인한 유저의 PK를 얻어옴
     * @return [String] PK
     */
    public static Long getCurrentMemberPk() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userPk = userDetails.getMember().getMemberId();
        return userPk;

    }

}
