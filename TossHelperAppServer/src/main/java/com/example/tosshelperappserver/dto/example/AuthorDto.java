package com.example.tosshelperappserver.dto.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private Long id;

    private String name;

    private int age;

    private String sex;

    private int averageAge;

}
