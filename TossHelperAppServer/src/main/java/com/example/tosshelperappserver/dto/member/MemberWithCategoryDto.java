package com.example.tosshelperappserver.dto.member;

import com.example.tosshelperappserver.dto.tag.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "MEM_RES_03 : 회원 데이터 + 카테고리")
public class MemberWithCategoryDto {

    @Schema(description = "사용자 PK", example = "1")
    private Long memberId;

    @Schema(description = "사용자 이메일", example = "testtest@gmail.com")
    private String email;

    @Schema(description = "사용자 이름", example = "John Doe")
    private String name;

    @Schema(description = "태그")
    private List<TagDto> tags;

}