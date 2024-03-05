package com.example.tosshelperappserver.repository.tag;

import com.example.tosshelperappserver.domain.Member;

public interface TagCustomRepository {
    boolean isExistByMemberOwnTagName(Long memberId, String tagName);
}
