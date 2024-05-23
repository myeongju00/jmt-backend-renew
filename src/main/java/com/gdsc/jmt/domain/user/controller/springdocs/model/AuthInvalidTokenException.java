package com.gdsc.jmt.domain.user.controller.springdocs.model;


import com.gdsc.jmt.global.messege.AuthMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthInvalidTokenException {

    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "ID TOKEN이 유효하지 않습니다")
    String message = AuthMessage.INVALID_TOKEN.getMessage();

    @Schema(description = "", example = "INVALID_TOKEN")
    String code = AuthMessage.INVALID_TOKEN.toString();
}
