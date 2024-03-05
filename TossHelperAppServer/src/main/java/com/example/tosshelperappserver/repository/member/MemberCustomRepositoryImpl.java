package com.example.tosshelperappserver.repository.member;
import com.example.tosshelperappserver.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.example.tosshelperappserver.domain.QMember.member;


@Repository
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Member findAllLeftFetchJoin(Long id) {


        return jpaQueryFactory.selectFrom(member)
                .where(member.memberId.eq(id))
                .leftJoin(member.tags)
                .fetchJoin()
                .fetchOne();
    }


}
