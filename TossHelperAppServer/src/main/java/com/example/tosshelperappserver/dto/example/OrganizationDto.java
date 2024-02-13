package com.example.tosshelperappserver.dto.example;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizationDto {

    private Long id;

    private String orgName;

    @QueryProjection
    public OrganizationDto(Long id, String orgName) {
        this.id = id;
        this.orgName = orgName;
    }


}
