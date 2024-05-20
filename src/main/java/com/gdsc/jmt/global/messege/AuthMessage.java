package com.gdsc.jmt.global.messege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthMessage implements ResponseMessage{
    LOGIN_BAD_REQUEST("잘못된 접근입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("ID TOKEN이 유효하지 않습니다", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;
}
