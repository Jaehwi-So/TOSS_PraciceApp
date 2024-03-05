package com.example.tosshelperappserver.service.tag;

import com.example.tosshelperappserver.dto.tag.TagRequestDto;

public interface TagService {

    /**
     * [태그 추가]
     * 태그 추가
     * @param TagRequestDto
     */
    Long addTag(TagRequestDto member, Long MemberId);

}
