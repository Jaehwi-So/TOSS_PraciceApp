package com.example.tosshelperappserver.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "TAG_RES_01 : 태그 데이터")
public class TagDto {

    @Schema(description = "태그 PK", example = "1")
    private Long tagId;

    @Schema(description = "태그 이름", example = "빈출 영문법")
    private String name;
}

