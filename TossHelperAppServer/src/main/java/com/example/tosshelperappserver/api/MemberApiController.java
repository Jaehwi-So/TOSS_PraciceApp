package com.example.tosshelperappserver.api;

import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Member", description = "Member API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    @PostMapping("/{pathValue}")
    @Operation(summary = "Get member profile", description = "특정 멤버의 상세 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })
    public String getMemberProfile(
            @PathVariable
            @Schema(description = "Member ID", example = "1")
            Long pathValue,

            // TODO: Replace with member ID from JWT or that from any other authentication method
            @Parameter(name = "paramValue", description = "로그인 유저 ID 값", example = "3", required = true)
            @RequestParam final Long paramValue,

            @RequestBody @Valid MemberJoinRequestDto request
    ) {
        return "Hello World!";
    }

    @PostMapping("/hello")
    @Operation(summary = "Get member profile", description = "특정 멤버의 상세 정보를 조회한다.")
    public String getHello(
    ) {
        return "Hello World!";
    }


}
