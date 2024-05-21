package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.domain.user.command.controller.springdocs.model.BadRequestException;
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
@Operation(summary = "안드로이드 기기를 위한 애플 로그인 API", description = "애플 로그인 및 회원가입을 처리하는 API 입니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(
                responseCode = "400",
                description = "잘못된 기기에서의 접근입니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class))),
})
public @interface AndroidAppleLoginSpringDocs {
}
