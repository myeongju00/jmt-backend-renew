package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.global.controller.springdocs.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API 입니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "401", description = "AccessToken이 유효하지 않습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
})
public @interface RemoveSpringDocs {
}
