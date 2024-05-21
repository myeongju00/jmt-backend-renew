package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.domain.user.command.controller.springdocs.model.RefreshTokenInvalidException;
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
@Operation(summary = "로그아웃 API", description = "서버 측에 등록되어 있는 RefreshToken을 제거합니다.\n" +
        "요구사항 : 클라이언트 측에서는 쿠키에 AccessToken, RefreshToken을 제거해주세요!!!!")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "RefreshToken이 유효하지 않습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenInvalidException.class))),
        @ApiResponse(responseCode = "401", description = "AccessToken이 유효하지 않습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
})
public @interface LogoutSpringDocs {
}
