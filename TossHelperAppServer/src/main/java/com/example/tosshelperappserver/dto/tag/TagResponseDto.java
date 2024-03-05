package com.example.tosshelperappserver.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "TAG_RES_01 : 태그 추가 응답 DTO")
public class TagResponseDto {

    @Schema(description = "태그 PK", example = "1")
    private Long tagPk;

}