package com.example.tosshelperappserver.service.member;

import com.example.tosshelperappserver.common.constant.RoleType;
import com.example.tosshelperappserver.config.exception.custom.AlreadyExistElementException;
import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.dto.member.CustomUserInfoDto;
import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;
import com.example.tosshelperappserver.repository.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Long addMember(MemberJoinRequestDto inputMember) {
        Member exist = memberRepository.findMemberByEmail(inputMember.getEmail());
        if(exist != null){
            throw new AlreadyExistElementException("이미 존재하는 이메일입니다.");
        }
        inputMember.setPassword(encoder.encode(inputMember.getPassword()));
        Member member = modelMapper.map(inputMember, Member.class);
        member.setRole(RoleType.COMMON);
        member = memberRepository.save(member);
        return member.getMemberId();
    }

    @Override
    public CustomUserInfoDto getMemberInfoIncludePwdByEmail(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        CustomUserInfoDto dto = modelMapper.map(member, CustomUserInfoDto.class);
        return dto;
    }


}
