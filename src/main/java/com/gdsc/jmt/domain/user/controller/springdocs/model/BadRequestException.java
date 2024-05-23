package com.gdsc.jmt.domain.user.controller.springdocs.model;

import com.gdsc.jmt.global.messege.AuthMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BadRequestException {
    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "잘못된 접근입니다.")
    String message = AuthMessage.LOGIN_BAD_REQUEST.getMessage();

    @Schema(description = "", example = "LOGIN_BAD_REQUEST")
    String code = AuthMessage.LOGIN_BAD_REQUEST.toString();
}
