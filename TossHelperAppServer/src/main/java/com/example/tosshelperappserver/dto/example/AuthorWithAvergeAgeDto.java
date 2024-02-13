package com.example.tosshelperappserver.dto.example;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorWithAvergeAgeDto {

    private String name;

    private double averageAge;






    @QueryProjection
    public AuthorWithAvergeAgeDto(String name, double averageAge) {
        this.name = name;
        this.averageAge = averageAge;

    }
}
