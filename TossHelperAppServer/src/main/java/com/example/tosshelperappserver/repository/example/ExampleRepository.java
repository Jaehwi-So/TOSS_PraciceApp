package com.example.tosshelperappserver.repository.example;


import com.example.tosshelperappserver.domain.Member;
import com.example.tosshelperappserver.domain.example.Author;
import com.example.tosshelperappserver.domain.example.Book;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tosshelperappserver.domain.QMember.member;
import static com.example.tosshelperappserver.domain.example.QAuthor.author;
import static com.example.tosshelperappserver.domain.example.QBook.book;

@Repository
@AllArgsConstructor
public class ExampleRepository {

    private final JPAQueryFactory queryFactory;


    // 엔티티 리스트 조회
    public List<Book> findBookList() {
        List<Book> result = queryFactory
                .selectFrom(book)
                .fetch();
        return result;
    }


    // 엔티티 한 개 조회
    public Book findBookByTitle(String title) {
        Book result = queryFactory.selectFrom(book)
                .where(book.title.eq(title))
                .fetchOne();
        return result;
    }

    // 엔티티 컬럼 선택 조회
    public String findBookTitle(String title) {
        String result = queryFactory.select(book.title)
                .from(book)
                .where(book.title.eq(title))
                .fetchOne();
        return result;
    }

    // 엔티티 조회 (검색 조건)
    //10세~20세, 30세~50세 사이 조회
    public List<Author> findAuthorByCondition() {

        List<Author> result = queryFactory.selectFrom(author)
                .where(
                        author.age.notBetween(20, 30)
                                .and(author.age.gt(10))
                                .and(author.age.lt(50))
                )
                .fetch();


        return result;
    }

    // 엔티티 조회 (검색 조건 2)
    //10세~20세, 30세~50세 사이 조회 or John이 포함된 사람
    public List<Author> findAuthorByCondition2() {

        List<Author>  result = queryFactory.selectFrom(author)
                .where(
                        (
                                author.age.notBetween(20, 30)
                                .and(author.age.gt(10))
                                .and(author.age.lt(50))
                        ).or(
                                author.name.like("%John%")
                        )
                )
                .fetch();


        return result;

    }



    // OrderBy
    public List<Book> findAllBookListOrderBy() {
        List<Book> result = queryFactory
                .selectFrom(book)
                .orderBy(book.title.desc())
                .fetch();
        return result;
    }


    //페이지네이션
    public List<Book> findAllBookListPagenation(int offset, int limit) {
        List<Book> result = queryFactory
                .selectFrom(book)
                .offset(offset)
                .limit(limit)
                .fetch();
        return result;
    }


    //집계
    public List<Tuple> findAuthorAggregation() {
        List<Tuple> result = queryFactory
                .select(author.count(), author.age.avg())
                .from(author)
                .fetch();
        return result;
    }

    //그룹화
    public List<Tuple> findAuthorGroupBy() {
        List<Tuple> result = queryFactory
                .select(author.count(), author.age.avg())
                .from(author)
                .groupBy(author.organization.orgName)
                .having(author.organization.orgName.ne(""))
                .fetch();
        return result;
    }



    // 여러 엔티티 조회
//    SELECT Book.*
//    FROM Book
//    INNER JOIN Author ON Book.author_id = Author.id
//    WHERE Author.name = 'John Doe';
    public List<Book> findBooksByAuthorName(String name) {
        List<Book> result = queryFactory
                .selectFrom(book)
                .where(book.author.name.eq(name))
                .fetch();
        return result;
    }

}
