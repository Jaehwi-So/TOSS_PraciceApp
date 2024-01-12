package com.example.tosshelperappserver.repository;

import com.example.tosshelperappserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
