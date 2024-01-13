package com.example.tosshelperappserver.api;

import com.example.tosshelperappserver.config.exception.ErrorResponseDto;
import com.example.tosshelperappserver.dto.member.swagger.MemberJoinRequestDto;
import com.example.tosshelperappserver.dto.member.swagger.MemberJoinResponseDto;
import com.example.tosshelperappserver.service.MemberService;
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


@Tag(name = "Member", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 시킨다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = MemberJoinResponseDto.class))}),
            @ApiResponse(responseCode = "412", description = "Validate Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
    })
    public ResponseEntity<MemberJoinResponseDto> getMemberProfile(
            @Valid @RequestBody MemberJoinRequestDto request
    ) {

        Long memberId = memberService.addMember(request);
        MemberJoinResponseDto response = new MemberJoinResponseDto(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
