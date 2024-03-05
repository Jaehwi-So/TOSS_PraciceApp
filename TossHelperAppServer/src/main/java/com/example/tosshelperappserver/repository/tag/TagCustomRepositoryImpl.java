package com.example.tosshelperappserver.repository.tag;

import com.example.tosshelperappserver.domain.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.tosshelperappserver.domain.QTag.tag;


@Repository
@AllArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public boolean isExistByMemberOwnTagName(Long memberId, String tagName){

        Tag searchTag = jpaQueryFactory.select(tag)
                .from(tag)
                .where(tag.member.memberId.eq(memberId)
                        .and(tag.name.eq(tagName)))
                .fetchFirst();

        boolean exist = searchTag != null;
        return exist;


    }

}
