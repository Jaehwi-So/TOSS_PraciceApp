package com.example.tosshelperappserver.service;

import com.example.tosshelperappserver.common.constant.RoleType;
import com.example.tosshelperappserver.config.exception.custom.AlreadyExistElementException;
import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.dto.member.CustomUserInfoDto;
import com.example.tosshelperappserver.dto.member.MemberWithCategoryDto;
import com.example.tosshelperappserver.dto.member.swagger.MemberJoinRequestDto;
import com.example.tosshelperappserver.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberJpaRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Long addMember(MemberJoinRequestDto inputMember) {
        Member exist = memberJpaRepository.findMemberByEmail(inputMember.getEmail());
        if(exist != null){
            throw new AlreadyExistElementException("이미 존재하는 이메일입니다.");
        }
        inputMember.setPassword(encoder.encode(inputMember.getPassword()));
        Member member = modelMapper.map(inputMember, Member.class);
        member.setRole(RoleType.COMMON);
        member = memberJpaRepository.save(member);
        return member.getMemberId();
    }

    @Override
    public CustomUserInfoDto getMemberInfoIncludePwdByEmail(String email) {
        Member member = memberJpaRepository.findMemberByEmail(email);
        CustomUserInfoDto dto = modelMapper.map(member, CustomUserInfoDto.class);
        return dto;
    }

    @Override
    public MemberWithCategoryDto getMemberInfoWithOwnCategory(Long id) {
        Member member = memberJpaRepository.findAllLeftFetchJoin(id);
        MemberWithCategoryDto dto = modelMapper.map(member, MemberWithCategoryDto.class);
        return dto;
    }
}
