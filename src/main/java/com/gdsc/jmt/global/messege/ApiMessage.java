package com.gdsc.jmt.global.messege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiMessage implements ResponseMessage{
    NAVER_LOCATION_CONVERT_SUCCESS("네이버 주소 변환에 성공하였습니다.", HttpStatus.OK);

    private final String message;
    private final HttpStatus status;
}
