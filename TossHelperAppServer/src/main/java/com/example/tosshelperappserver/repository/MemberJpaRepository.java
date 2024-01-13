package com.example.tosshelperappserver.repository;

import com.example.tosshelperappserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query(value= "select m from Member m where m.email = :email" )
    Member findMemberByEmail(@Param("email") String email);

}
