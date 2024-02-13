package com.example.tosshelperappserver.dto.example;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorWithBooksDto {

    private Long id;

    private String name;

    private int age;

    private String gender;

    private List<BookDto> book = new ArrayList<>();



    @QueryProjection
    public AuthorWithBooksDto(Long id, String name, int age, String gender, List<BookDto> book) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.book = book;
    }
}
