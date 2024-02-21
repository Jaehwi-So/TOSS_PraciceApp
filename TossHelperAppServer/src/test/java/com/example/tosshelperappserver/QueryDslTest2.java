package com.example.tosshelperappserver;


import com.example.tosshelperappserver.domain.example.*;
import com.example.tosshelperappserver.dto.example.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.tosshelperappserver.domain.example.QAuthor.author;
import static com.example.tosshelperappserver.domain.example.QBook.book;
import static com.example.tosshelperappserver.domain.example.QOrganization.organization;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback
public class QueryDslTest2 {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Autowired
    private ModelMapper modelMapper;



    //테스트 코드 이전에 실행된다.
    @BeforeEach
    public void before(){
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
        Organization organ = new Organization();
        organ.setOrgName("My Organization");
        em.persist(organ);
    }


    //프로퍼티 접근 (Setter)
    @Test
    public void projectionTestBySetter() throws Exception {
        //given

        //when
        List<AuthorDto> results = queryFactory
                .select(Projections.bean(AuthorDto.class,
                        author.id,
                        author.name,
                        author.age,
                        ExpressionUtils.as(author.gender, "sex")
//                        author.gender.as("sex")
                ))
                .from(author)
                .fetch();

        print("projectionTestBySetter", results);

        //then
    }


    //생성자로 접근
    @Test
    public void projectionTestByConstuctor() throws Exception {
        //given

        //when
        List<AuthorDto> results = queryFactory
                .select(Projections.constructor(AuthorDto.class,
                        author.id,
                        author.name,
                        author.age,
                        author.gender
                ))
                .from(author)
                .fetch();

        print("projectionTestBySetter", results);

        //then
    }


    //필드 직접 접근
    @Test
    public void projectionTestByFields() throws Exception {
        //given

        //when
        List<AuthorDto> results = queryFactory
                .select(Projections.fields(AuthorDto.class,
                        author.id,
                        author.name,
                        author.age,
//                        ExpressionUtils.as(author.gender, "sex")
                        author.gender.as("sex")
                ))
                .from(author)
                .fetch();

        print("projectionTestByConstructor", results);

        //then

    }


    @Test
    public void projectionTestByCase() throws Exception {
        //given

        //when
        List<AuthorDto> results = queryFactory
                .select(Projections.fields(AuthorDto.class,
                        author.id,
                        author.name,
                        author.age,
                        ExpressionUtils.as(author.gender
                                .when("M").then("Male")
                                .otherwise("Female"),
                                "sex"))
                        )
                .from(author)
                .fetch();

        print("projectionTestWithCase", results);

        //then

    }



    @Test
    public void projectionTestWithSubquery() throws Exception {
        //given
        QAuthor subAuthor = new QAuthor("subAuthor");

        //when
        List<AuthorDto> results = queryFactory
                .select(Projections.fields(AuthorDto.class,
                        author.id,
                        author.name,
                        author.age,
                        author.gender.as("sex"),
                        ExpressionUtils.as(
                                select(
                                    Expressions.numberTemplate(Integer.class, "{0}",
                                                MathExpressions.round(subAuthor.age.avg(),0)
                                        )
                                ).from(subAuthor),
                                "averageAge"

                        )
                ))
                .from(author)
                .fetch();

        print("projectionTestWithSubquery", results);

        //then

    }


    //Q파일 생성 단점
    //DTO가 QueryDSL에 대한 의존성이 생김.
    //일반적으로 DTO는 여러 Layer에서 사용되는데 -> QueryDSL에 의존적으로 설계되게 됨
    @Test
    public void queryQtypeProjectionsToOne() throws Exception {
        //given

        List<AuthorWithOrganizationDto> results = queryFactory
                .select(new QAuthorWithOrganizationDto(author.id, author.name, author.age, author.gender,
                        new QOrganizationDto(author.organization.id, author.organization.orgName)))
                .from(author)
                .join(author.organization, organization)
                .fetch();

        print("queryProjections", results);

        //then

    }


    @Test
    public void queryQtypeProjectionsToMany() throws Exception {
        //given

        List<AuthorWithBooksDto> results = queryFactory
                .from(author)
                .join(author.book, book)
                .transform(
                    GroupBy.groupBy(author.id).list(
                        new QAuthorWithBooksDto(
                            author.id,
                            author.name,
                            author.age,
                            author.gender,
                            GroupBy.list(new QBookDto(
                                    book.id,
                                    book.title
                            )
                        )
                    )
                ));


        System.out.println(results);

        //then

    }


    @Test
    public void queryProjectionsToOne() throws Exception {
        //given

        //when
        List<AuthorWithOrganizationDto> results = queryFactory
                .select(Projections.fields(AuthorWithOrganizationDto.class,
                        author.id,
                        author.name,
                        author.age,
                        author.gender,
                        Projections.fields(
                                OrganizationDto.class,
                                ExpressionUtils.as(organization.id, "id"),
                                ExpressionUtils.as(organization.orgName, "orgName")
                        ).as("organization")
                ))
                .from(author)
                .leftJoin(author.organization, organization)
                .fetch();




        System.out.println(results);

        //then

    }



    @Test
    public void queryProjectionsToMany() throws Exception {
        //given

        //when
        List<AuthorWithBooksDto> results = queryFactory
                .from(author)
                .join(author.book, book)
                .transform(
                        GroupBy.groupBy(author.id).list(
                                Projections.fields(
                                        AuthorWithBooksDto.class,
                                        author.id,
                                        author.name,
                                        author.age,
                                        author.gender,
                                        GroupBy.list(
                                                Projections.fields(
                                                        BookDto.class,
                                                        book.id,
                                                        book.title
                                                )
                                        ).as("book")
                                )
                        ));




        System.out.println(results);

        //then

    }


    @Test
    public void matchDtoToOne() throws Exception {
        //given

        List<Author> results = queryFactory
                .select(author)
                .from(author)
                .join(author.organization, organization)
                .fetch();

        List<AuthorWithOrganizationDto> dtos = results.stream()
                .map(e -> new AuthorWithOrganizationDto(e.getId(), e.getName(), e.getAge(), e.getGender(),
                        new OrganizationDto(
                                e.getOrganization().getId(), e.getOrganization().getOrgName()
                        ))
                )
                .collect(Collectors.toList());


        List<AuthorWithOrganizationDto> dtos2 = results.stream()
                        .map(data -> modelMapper.map(data, AuthorWithOrganizationDto.class))
                        .collect(Collectors.toList());

        print("queryProjections", dtos);
        print("queryProjections", dtos2);

        //then

    }


    @Test
    public void matchDtoToMany() throws Exception {
        //given

        List<Author> results = queryFactory
                .select(author)
                .from(author)
                .join(author.book, book)
                .fetch();
//
        List<AuthorWithBooksDto> dtos = results.stream()
                .map(e -> new AuthorWithBooksDto(e.getId(), e.getName(), e.getAge(), e.getGender(),
                        e.getBook().stream()
                                .map(book -> new BookDto(
                                        book.getId(),
                                        book.getTitle()
                                )).collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());


        List<AuthorWithBooksDto> dtos2 = results.stream()
                .map(data -> modelMapper.map(data, AuthorWithBooksDto.class))
                .collect(Collectors.toList());
//
        print("queryProjections", dtos);
        print("queryProjections", dtos2);

        //then

    }



    @Test
    public void matchDtoTuple(){

        QAuthor subAuthor = new QAuthor("subAuthor");
        List<Tuple> results = queryFactory
                .select(author.name,
                        select(subAuthor.age.avg().as("averageAge"))
                                .from(subAuthor)
                )
                .from(author)
                .fetch();

        List<AuthorWithAvergeAgeDto> dtos = results.stream()
                .map(e -> new AuthorWithAvergeAgeDto(e.get(author.name), e.get(1, Double.class)))
                .collect(Collectors.toList());

        print("queryProjections", dtos);

    }


    @Test
    public void usingDynamicQuery() throws Exception {
        //given

        List<Author> authors = DynamicQueryWithBooleanBuilder("John Doe", 25);
        System.out.println(authors);

        authors = DynamicQueryWithBooleanBuilder("Jane Smith", null);
        System.out.println(authors);

        authors = DynamicQueryWithBooleanBuilder(null, null);
        System.out.println(authors);
        //when

        //then

    }

    private List<Author> DynamicQueryWithBooleanBuilder(String name, Integer age) {

        BooleanBuilder predicate = new BooleanBuilder();

        if (name != null) {
            predicate.and(author.name.eq(name));
        }

        if (age != null) {
            predicate.and(author.age.eq(age));
        }

        return queryFactory
                .selectFrom(author)
                .where(predicate)
                .fetch();

    }


    @Test
    public void usingWhereDynamicParam() throws Exception {
        //given

        List<Author> authors = WhereDynamicParam("John Doe", 25);
        System.out.println(authors);

        authors = WhereDynamicParam("Jane Smith", null);
        System.out.println(authors);

        authors = WhereDynamicParam(null, null);
        System.out.println(authors);
        //when

        //then

    }

    private List<Author> WhereDynamicParam(String name, Integer age) {
        return queryFactory
                .selectFrom(author)
                .where(nameEq(name), ageEq(age))
                .fetch();
    }


    private BooleanExpression nameEq(String nameCond) {
        return nameCond != null ? author.name.eq(nameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? author.age.eq(ageCond) : null;
    }




    private <T> void print(String title, List<T> tuples){
        System.out.println("Test : " + title);
        for(T tuple : tuples){
            System.out.println("Tuple : " + tuple);
        }
    }





}


/**
 * 1. 튜플 타입은 사용하게 된다면 Repository 안에서만 쓰고, DTO로 반환하도록
 */