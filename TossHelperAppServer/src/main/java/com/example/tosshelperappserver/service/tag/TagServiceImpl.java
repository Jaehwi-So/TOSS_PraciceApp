package com.example.tosshelperappserver.service.tag;

import com.example.tosshelperappserver.config.exception.custom.AlreadyExistElementException;
import com.example.tosshelperappserver.config.exception.custom.NoSuchElementFoundException;
import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.domain.Tag;
import com.example.tosshelperappserver.dto.tag.MemberWithTagDto;
import com.example.tosshelperappserver.dto.tag.TagRequestDto;
import com.example.tosshelperappserver.repository.member.MemberRepository;
import com.example.tosshelperappserver.repository.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Long addTag(TagRequestDto inputTag, Long memberId) {
        Member member = memberRepository.findMemberByMemberId(memberId);
        if(member == null){
            throw new NoSuchElementFoundException("존재하지 않는 회원입니다");
        }
        if(tagRepository.isExistByMemberOwnTagName(memberId, inputTag.getName())){
            throw new AlreadyExistElementException("이미 회원에게 존재하는 태그 이름입니다.");
        }
        Tag tag = modelMapper.map(inputTag, Tag.class);
        tag.setMember(member);
        Tag result = tagRepository.save(tag);
        return result.getTagId();
    }

    @Override
    public MemberWithTagDto getTagInfoByMember(Long memberId) {
        Member member = memberRepository.findAllLeftFetchJoin(memberId);
        MemberWithTagDto dto = modelMapper.map(member, MemberWithTagDto.class);
        return dto;
    }

}
