package com.example.tosshelperappserver.dto.category;

import com.example.tosshelperappserver.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "CAT_RES_01 : 카테고리 데이터")
public class CategoryDto {

    @Schema(description = "카테고리 PK", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "빈출 영문법")
    private String name;
}

