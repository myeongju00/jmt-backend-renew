package com.gdsc.jmt.global.controller.springdocs;

import com.gdsc.jmt.global.messege.UserMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserNotFoundException {
    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "user 정보를 찾을 수 없습니다.")
    String message = UserMessage.USER_NOT_FOUND.getMessage();

    @Schema(description = "", example = "USER_NOT_FOUND")
    String code = UserMessage.USER_NOT_FOUND.toString();
}
