package com.gdsc.jmt.global.messege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GroupMessage implements ResponseMessage{
    CREATED_GROUP("그룹을 생성하였습니다." , HttpStatus.OK),
    JOIN_GROUP("그룹 가입에 성공하였습니다." , HttpStatus.OK),
    LEAVE_GROUP("그룹 탈퇴에 성공하였습니다." , HttpStatus.OK),
    FIND_GROUP("그룹 조회에 성공하였습니다." , HttpStatus.OK),
    SELECTED_GROUP("그룹을 선택하였습니다." , HttpStatus.OK),
    USER_NOT_FOUND_IN_GROUP("이미 탈퇴하였거나 그룹에 사용자가 존재하지 않습니다." , HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS_IN_GROUP("이미 그룹에 가입하였습니다." , HttpStatus.CONFLICT),
    GROUP_NOT_FOUND("해당 그룹은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    private final String message;
    private final HttpStatus status;

}
