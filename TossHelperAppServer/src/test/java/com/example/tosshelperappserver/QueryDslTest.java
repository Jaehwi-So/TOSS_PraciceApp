package com.example.tosshelperappserver;


import com.example.tosshelperappserver.domain.example.Author;
import com.example.tosshelperappserver.domain.example.Book;
import com.example.tosshelperappserver.domain.example.Organization;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.dialect.TiDBDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.tosshelperappserver.domain.example.QAuthor.author;
import static com.example.tosshelperappserver.domain.example.QBook.book;
import static com.example.tosshelperappserver.domain.example.QReview.review;
import static org.assertj.core.api.Assertions.*;
import static com.example.tosshelperappserver.domain.example.QOrganization.organization;

@SpringBootTest
@Transactional
//@Rollback
public class QueryDslTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;



    //테스트 코드 이전에 실행된다.
    @BeforeEach
    public void before(){
        this.queryFactory = new JPAQueryFactory(em);
        Organization organ = new Organization();
        organ.setOrgName("My Organization");
        em.persist(organ);
    }

    @Test
    public void exampleTest(){
        //given

        //when
        Organization result = queryFactory.selectFrom(organization)
                .where(organization.orgName.eq("My Organization"))
                .fetchFirst();

        //then
        assertThat(result.getOrgName()).isEqualTo("My Organization");
    }


    /**
     * 저자 A가 작성한 모든 책 조회
     * Lazy이기 때문에 book만 조회되고, 연관된 author은 조회되지 않는다.
     * @throws Exception
     */
    @Test
    public void join() throws Exception {
        //given


        //when
        List<Book> books = queryFactory
                .selectFrom(book)
                .leftJoin(book.author, author)
                .where(author.name.eq("John Doe"))
                .fetch();

        //then
        assertThat(books)
                .extracting("id")
                .containsExactly(1L, 2L);

        print("join", books);

    }

    /**
     * 저자 A가 작성한 모든 책 조회
     * @throws Exception
     */
    @Test
    public void joinWithSelectRelation() throws Exception {
        //given


        //when
        List<Tuple> results = queryFactory
                .select(book, author)
                .from(book)
                .leftJoin(book.author, author)
                .where(author.name.eq("John Doe"))
                .fetch();


        print("joinWithSelectRelation", results);

    }


    @Test
    public void cartesianProduct() throws Exception {
        //given

        //when
        List<Tuple> results = queryFactory
                .select(book, author)
                .from(book, author)
                .fetch();

        //then

    }

    @Test
    public void thetaJoin() throws Exception {
        //given

        //when
        List<Book> books = queryFactory
                .select(book)
                .from(book, author)
                .where(book.author.id.eq(author.id))
                .fetch();

        //then

    }


    /**
     * 책과 저자를 조인하면서, 저자의 이름이 John Doe인 저자만 조인, 책은 모두 조회
     * 해당 경우 외부 조인이므로 where이 아닌, on문을 통해 Join 대상을 필터링하고, Left Join으로는 모든 책을 조회해야 한다.
     * @throws Exception
     */
    @Test
    public void leftJoinOnFiltering() throws Exception {
        //given

        //when
        List<Tuple> results = queryFactory
                .select(book, author)
                .from(book)
                .leftJoin(book.author, author)
                .on(author.name.eq("John Doe"))
                .fetch();

        print("leftJoinOnFiltering", results);

        //then

    }


    /**
     * 책과 저자를 조인하면서, 저자의 이름이 John Doe인 저자와 책만 조인
     * 해당 경우 내부 조인이므로 on 혹은 where 모두 활용 가능하다.
     * @throws Exception
     */
    @Test
    public void innerJoinOnFiltering() throws Exception {
        //given

        //when
        List<Tuple> results = queryFactory
                .select(book, author)
                .from(book)
                .innerJoin(book.author, author)
                .on(author.name.eq("John Doe"))  // == where(author.name.eq("John Doe"))
                .fetch();

        print("leftJoinOnFiltering", results);

        //then

    }


    /**
     * 서로 관계없는 필드를 통해 외부 조인 수행
     * 책의 리뷰들 중, 책의 이름이 포함된 리뷰를 Join한다.
     * @throws Exception
     */
    @Test
    public void innerJoinOnFiltering2() throws Exception {
        //given

        //when
        List<Tuple> results = queryFactory
                .select(book, review)
                .from(book)
                // .innerJoin(book.review, review)
                .innerJoin(review)  //외래키를 통한 Join을 하지 않는다.
                .on(review.comment.contains(book.title))
                .fetch();

        print("innerJoinOnFiltering2", results);

        //then

    }


    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNot(){
        em.flush();
        em.clear();

        List<Book> books = queryFactory
                .selectFrom(book)
                .join(book.author, author)
                .where(author.name.eq("John Doe"))
                .fetch();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(books.get(0).getAuthor());
        assertThat(loaded).as("로드되지 않음").isFalse();
        print("Fetch Join 미적용", books);

    }

    @Test
    public void fetchJoin(){
        em.flush();
        em.clear();

        List<Book> books = queryFactory
                .selectFrom(book)
                .join(book.author, author).fetchJoin()
                .where(author.name.eq("John Doe"))
                .fetch();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(books.get(0).getAuthor());
        assertThat(loaded).as("로드됨").isTrue();



    }



    private <T> void print(String title, List<T> tuples){
        System.out.println("Test : " + title);
        for(T tuple : tuples){
            System.out.println("Tuple : " + tuple);
        }
    }

//
//    @Test
//    public void complexedJoin() throws Exception {
//        //given
//
//        //when
//        List<Organization> results = queryFactory
//                .select(organization)
//                .from(organization)
//                .innerJoin(organization.authors, author)
//                .on(author.gender.eq("M"))
//                .innerJoin(author.book, book)
//                .fetch();
//
//        for(Organization org : results){
//            System.out.println(org);
//            for(Author author : org.getAuthors()){
//                System.out.println(author);
//            }
//        }
//
//        print("complexedJoin", results);
//
//        //then
//
//    }

}
