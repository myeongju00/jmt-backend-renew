package com.gdsc.jmt.domain.user.controller.springdocs.model;

import com.gdsc.jmt.global.messege.UserMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RefreshTokenInvalidException {

    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "RefreshToken이 유효하지 않습니다.")
    String message = UserMessage.REFRESH_TOKEN_INVALID.getMessage();

    @Schema(description = "", example = "REISSUE_FAIL")
    String code = UserMessage.REFRESH_TOKEN_INVALID.toString();
}
