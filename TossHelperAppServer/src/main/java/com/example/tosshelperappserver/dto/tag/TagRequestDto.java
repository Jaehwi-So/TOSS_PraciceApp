package com.example.tosshelperappserver.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "TAG_REQ_01 : 태그 요청 데이터")
public class TagRequestDto {

    @Schema(description = "태그 이름", example = "빈출 영문법")
    private String name;

    @Schema(description = "태그 내용", example = "~~")
    private String content;
}

