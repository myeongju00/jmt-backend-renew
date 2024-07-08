package com.gdsc.jmt.domain.user.controller.springdocs.model;

import com.gdsc.jmt.global.messege.UserMessage;
import io.swagger.v3.oas.annotations.media.Schema;

public class NicknameConflictErrorException {
    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "이미 존재하는 닉네임입니다.")
    String message = UserMessage.NICKNAME_IS_DUPLICATED.getMessage();

    @Schema(description = "", example = "NICKNAME_IS_DUPLICATED")
    String code = UserMessage.NICKNAME_IS_DUPLICATED.getMessage();
}
