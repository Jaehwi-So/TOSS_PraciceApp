package com.example.tosshelperappserver.config.security.user;

import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.dto.member.CustomUserInfoDto;
import com.example.tosshelperappserver.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        CustomUserInfoDto dto = mapper.map(member, CustomUserInfoDto.class);

        return new CustomUserDetails(dto);
    }
}