package com.example.tosshelperappserver.repository;

import com.example.tosshelperappserver.domain.Category;
import com.example.tosshelperappserver.domain.Member;

import java.util.List;

public interface MemberCustomRepository {

    Member findAllLeftFetchJoin(Long id);
}
