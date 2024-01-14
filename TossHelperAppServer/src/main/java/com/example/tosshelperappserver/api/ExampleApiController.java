package com.example.tosshelperappserver.api;

import com.example.tosshelperappserver.common.constant.AuthConstant;
import com.example.tosshelperappserver.dto.member.swagger.MemberJoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Example", description = "Example API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example")
public class ExampleApiController {

    @PostMapping("/{pathValue}")
    @PreAuthorize(AuthConstant.AUTH_COMMON)
    @Operation(summary = "Get member profile", description = "API Description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public String example(
            @PathVariable
            @Schema(description = "Member ID", example = "1")
            Long pathValue,

            // TODO: Replace with member ID from JWT or that from any other authentication method
            @Parameter(name = "paramValue", description = "Parameter Value", example = "3", required = true)
            @RequestParam final Long paramValue,

            @RequestBody @Valid MemberJoinRequestDto request
    ) {
        return "Hello World!";
    }




}
