package com.gdsc.jmt.global.messege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryMessage implements ResponseMessage{
    CATEGORY_FIND_SUCCESS("카테고리 조회에 성공하였습니다.", HttpStatus.OK),
    CATEGORY_FIND_FAIL("해당 카테고리가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    CATEGORY_LIST_SUCCESS("전체 카테고리 조회에 성공하였습니다.", HttpStatus.OK);

    private final String message;
    private final HttpStatus status;
}
