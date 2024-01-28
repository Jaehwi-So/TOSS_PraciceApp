package com.example.tosshelperappserver;

import com.example.tosshelperappserver.domain.example.Author;
import com.example.tosshelperappserver.domain.example.Book;
import com.example.tosshelperappserver.repository.example.ExampleRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.core.Is;
import org.hamcrest.core.StringStartsWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class ExampleTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ExampleRepository exampleRepository;


    @BeforeEach
    public void before(){
//        JPAQueryFactory factory = new JPAQueryFactory(em);
//        this.exampleRepository = new ExampleRepository(factory);
    }
    @Test
    @DisplayName("Author 찾기")
    public void testFindByAuthor() {
        // Given
        String parameter = "John Doe";

        // When
        List<Book> books = exampleRepository.findBooksByAuthorName(parameter);

        // Then
        System.out.println("Test!!");
        System.out.println(books.get(0).getTitle());
//        assertThat(book.getTitle()).isEqualTo(parameter);
    }

    @Test
    @DisplayName("Author 찾기")
    public void testFindByAuthor2() {
        // Given
        String parameter = "John Doe";

        // When
        List<Author> authors = exampleRepository.findAuthorByCondition2();

        // Then
        System.out.println("Test!!");
        for(Author author : authors){
            System.out.println(author.getName());
        }

//        assertThat(book.getTitle()).isEqualTo(parameter);
    }
}
