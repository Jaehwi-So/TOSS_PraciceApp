package com.example.tosshelperappserver;

import com.example.tosshelperappserver.domain.example.Author;
import com.example.tosshelperappserver.domain.example.Book;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.tosshelperappserver.domain.example.QAuthor.author;
import static com.example.tosshelperappserver.domain.example.QBook.book;
import static com.example.tosshelperappserver.domain.example.QReview.review;

@SpringBootTest
public class QueryTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;


    @BeforeEach
    public void before(){
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Test
    @DisplayName("Author 찾기")
    public void testFindByAuthor() {
        // Given
        String parameter = "John Doe";
        String parameter2 = "Introduction to SQL";

        // When
        Object result = findBookList();
        result = findBookByTitle(parameter2);
        result = findAuthorByCondition();
        result = findAuthorByCondition2();
        result = findBookListOrderBy();
        result = findBookListPagenation(0, 2);
        result = findAuthorAggregation();
        result = findAuthorGroupBy();
        result = findAuthorGroupBy2();
        // Then
//        assertThat(book.getTitle()).isEqualTo(parameter);
    }

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
    public List<String> findBookListTitle() {
        List<String> result = queryFactory.select(book.title)
                .from(book)
                .fetch();
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
    public List<Book> findBookListOrderBy() {
        List<Book> result = queryFactory
                .selectFrom(book)
                .orderBy(book.title.desc())
                .fetch();
        return result;
    }


    //페이지네이션
    public List<Book> findBookListPagenation(int offset, int limit) {
        QueryResults<Book> res = queryFactory
                .selectFrom(book)
                .offset(offset)
                .limit(limit)
                .fetchResults();

        System.out.println("Total : " + res.getTotal());
        System.out.println("Limit : " + res.getLimit());
        System.out.println("Offset : " + res.getOffset());
        List<Book> result = res.getResults();
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
                .select(author.organization.orgName, author.count(), author.age.avg())
                .from(author)
                .groupBy(author.organization.id, author.organization.orgName)
                .having(author.age.avg().lt(10))
                .fetch();

        System.out.println("OrgName: " + result.get(0).get(author.count()));
        return result;
    }


    public List<Tuple> findAuthorGroupBy2() {
        List<Tuple> result = queryFactory
                .select(author.gender, author.count(), author.age.avg())
                .from(author)
                .groupBy(author.gender)
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


    public List<Book> findBookWithAuthor() {
        List<Book> result = queryFactory
                .selectFrom(book)
//                .join(book.author, author)
                .where(book.author.name.eq("John Doe"))
                .fetch();
        return result;
    }
}
