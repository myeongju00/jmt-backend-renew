package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.domain.user.command.controller.springdocs.model.AuthInvalidTokenException;
import com.gdsc.jmt.global.controller.springdocs.ServerErrorException;
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
@Operation(summary = "구글 로그인 API", description = "구글 로그인 및 회원가입을 처리하는 API 입니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "401", description = "ID TOKEN가 유효하지 않음",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthInvalidTokenException.class))),
        @ApiResponse(
                responseCode = "500",
                description = "알수 없는 서버 에러 발생",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerErrorException.class))),
})
public @interface GoogleLoginSpringDocs {
}
