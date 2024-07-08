package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.global.controller.springdocs.ServerErrorException;
import com.gdsc.jmt.global.controller.springdocs.UserNotFoundException;
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
@Operation(summary = "로그인된 정보 조회 API", description = "현재 로그인 되어있는 사용자의 정보를 조회합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(
                responseCode = "404",
                description = "유저 정보를 찾을 수 없습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundException.class))
        ),
        @ApiResponse(
                responseCode = "500",
                description = "알수 없는 서버 에러 발생",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerErrorException.class))
        )
})
public @interface GetUserInfoSpringDocs {
}
