package com.gdsc.jmt.domain.user.controller.springdocs;

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
@Operation(summary = "프로필 사진 등록 API", description = "프로필 사진을 등록하는 API 입니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(
                responseCode = "500",
                description = "알수 없는 서버 에러 발생",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerErrorException.class))
        )
})
public @interface UpdateUserProfileImgSpringDocs {
}
