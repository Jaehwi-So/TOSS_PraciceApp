package com.example.tosshelperappserver.repository.member;

import com.example.tosshelperappserver.domain.Member;

public interface MemberCustomRepository {

    Member findAllLeftFetchJoin(Long id);
}
