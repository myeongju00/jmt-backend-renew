package com.gdsc.jmt.global.controller.springdocs;

import com.gdsc.jmt.global.messege.DefaultMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ServerErrorException {

    @Schema(description = "", nullable = true)
    String data = null;

    @Schema(description = "", example = "알수없는 서버 에러입니다.")
    String message = DefaultMessage.INTERNAL_SERVER_ERROR.getMessage();

    @Schema(description = "", example = "INTERNAL_SERVER_ERROR")
    String code = DefaultMessage.INTERNAL_SERVER_ERROR.toString();
}
