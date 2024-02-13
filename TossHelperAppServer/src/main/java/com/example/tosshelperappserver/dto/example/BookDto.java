package com.example.tosshelperappserver.dto.example;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    @QueryProjection
    public BookDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
