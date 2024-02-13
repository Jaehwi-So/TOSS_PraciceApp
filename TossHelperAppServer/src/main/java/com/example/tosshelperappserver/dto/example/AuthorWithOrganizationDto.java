package com.example.tosshelperappserver.dto.example;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorWithOrganizationDto {

    private Long id;

    private String name;

    private int age;

    private String gender;

    private OrganizationDto organization;




    @QueryProjection
    public AuthorWithOrganizationDto(Long id, String name, int age, String gender, OrganizationDto orgainzation) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.organization = orgainzation;
    }
}
