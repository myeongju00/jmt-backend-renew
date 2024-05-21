package com.gdsc.jmt.domain.user.controller.springdocs;

import com.gdsc.jmt.domain.restaurant.query.controller.springdocs.model.RestaurantNotFoundException;
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
@Operation(summary = "맛집 상세 조회 API", description = """
        맛집 상세정보를 조회합니다.
        200 상태코드 이면 맛집 상세정보를 조회 완료했다는 뜻입니다.
        404 상태코드 이면 맛집이 존재하지 않는다는 뜻입니다.""")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
        @ApiResponse(
                responseCode = "404",
                description = "맛집 위치정보를 찾을 수 없습니다.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantNotFoundException.class))
        )
})
public @interface FindDetailRestaurantSpringDocs {
}
