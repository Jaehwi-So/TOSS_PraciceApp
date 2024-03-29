package com.example.tosshelperappserver.api;

import com.example.tosshelperappserver.common.constant.AuthConstant;
import com.example.tosshelperappserver.config.exception.ErrorResponseDto;
import com.example.tosshelperappserver.dto.BasicResponseDto;
import com.example.tosshelperappserver.dto.member.MemberJoinRequestDto;
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
    @Operation(summary = "Example API Summary", description = "Your description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    public BasicResponseDto<MemberJoinRequestDto> exampleAPI(
            //Path Parameter
            @PathVariable
            @Schema(description = "Path Value", example = "1")
            Long pathValue,

            //Query Parameter
            @Parameter(name = "paramValue", description = "Parameter Value", example = "3", required = true)
            @RequestParam final Long paramValue,

            //Request Body
            @RequestBody @Valid MemberJoinRequestDto requestBody
    ) {
        String s = String.format("PathValue = %d , ParamValue = %s, Request Email : %s", pathValue, paramValue, requestBody.getEmail());
        BasicResponseDto response = new BasicResponseDto(true, "Example API Success",  requestBody);
        return response;
    }




}
