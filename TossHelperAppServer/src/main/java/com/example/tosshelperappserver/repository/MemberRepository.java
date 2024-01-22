package com.example.tosshelperappserver.repository;

import com.example.tosshelperappserver.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {


    // 쿼리 메서드
    Member findMemberByEmail(String email);

    // 동적 쿼리 생성
    @Query(value= "select m from Member m where m.memberId = :id and m.email = :email" )
    Member findMemberByIdAndEmail(@Param("id") Long id, @Param("email") String email);



}
