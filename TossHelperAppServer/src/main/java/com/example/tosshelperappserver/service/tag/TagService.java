package com.example.tosshelperappserver.service.tag;

import com.example.tosshelperappserver.dto.tag.MemberWithTagDto;
import com.example.tosshelperappserver.dto.tag.TagRequestDto;

public interface TagService {

    /**
     * [태그 추가]
     * 태그 추가
     * @param TagRequestDto
     */
    Long addTag(TagRequestDto member, Long MemberId);


    /**
     * [유저와 소유 카테고리 선택]
     * 유저 소유 카테고리 선택
     * @param MemberWithCategoryDto
     */
    MemberWithTagDto getTagInfoByMember(Long memberId);

}
