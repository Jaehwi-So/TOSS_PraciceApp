package com.example.tosshelperappserver.api;

import com.example.tosshelperappserver.config.exception.ErrorResponseDto;
import com.example.tosshelperappserver.config.security.SecurityUtil;
import com.example.tosshelperappserver.dto.tag.MemberWithTagDto;
import com.example.tosshelperappserver.dto.tag.TagRequestDto;
import com.example.tosshelperappserver.dto.member.MemberJoinResponseDto;
import com.example.tosshelperappserver.dto.tag.TagResponseDto;
import com.example.tosshelperappserver.service.tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Tag", description = "Tag API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagApiController {
    private final TagService tagService;


    //
    @PostMapping("")
    @Operation(summary = "태그 추가 API", description = "태그를 추가시킨다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = MemberJoinResponseDto.class))}),
            @ApiResponse(responseCode = "412", description = "Validate Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
    })
    public ResponseEntity<TagResponseDto> insertTag(
            @Valid @RequestBody TagRequestDto request
    ) {

        Long memberId = SecurityUtil.getCurrentMemberPk();
        Long result = tagService.addTag(request, memberId);

        TagResponseDto response = new TagResponseDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @GetMapping("/{memberId}")
    @Operation(summary = "유저와 연관 태그 선택", description = "태그 선택")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = MemberJoinResponseDto.class))}),
            @ApiResponse(responseCode = "412", description = "Validate Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
    })
    public ResponseEntity<MemberWithTagDto> getTagByMember(
            @PathVariable
            @Schema(description = "회원 ID", example = "1")
            Long memberId
    ) {

        MemberWithTagDto member = tagService.getTagInfoByMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }






}
