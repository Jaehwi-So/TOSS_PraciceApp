package com.example.tosshelperappserver.service;

import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;
import com.example.tosshelperappserver.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long addMember(MemberJoinRequestDto inputMember) {
        Member member = memberRepository.save(modelMapper.map(inputMember, Member.class));
        return member.getMemberId();
    }
}
