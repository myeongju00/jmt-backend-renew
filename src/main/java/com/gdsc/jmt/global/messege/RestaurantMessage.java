package com.gdsc.jmt.global.messege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RestaurantMessage implements ResponseMessage {

    RESTAURANT_LOCATION_FAIL("위치정보가 잘못되었습니다." , HttpStatus.BAD_REQUEST),
    RESTAURANT_LOCATION_CONFLICT("맛집 위치정보가 이미 등록이 되어있습니다." , HttpStatus.CONFLICT),
    RESTAURANT_LOCATION_CREATED("맛집 위치정보가 등록되었습니다." , HttpStatus.CREATED),

    RECOMMEND_RESTAURANT_CONFLICT("맛집이 이미 등록이 되어있습니다." , HttpStatus.CONFLICT),
    RECOMMEND_RESTAURANT_REGISTERABLE("해당 맛집을 등록할 수 있습니다." , HttpStatus.OK),
    RECOMMEND_RESTAURANT_DELETED("맛집이 삭제되었습니다." , HttpStatus.OK),
    RECOMMEND_RESTAURANT_REPORTED("맛집이 신고되었습니다." , HttpStatus.OK),
    FIND_ALL_REPORT_REASON("신고 사유를 조회하였습니다." , HttpStatus.OK),
    RECOMMEND_RESTAURANT_NOT_MATCH_OWNER("해당 맛집의 작성자가 아닙니다." , HttpStatus.OK),
    RECOMMEND_RESTAURANT_UPDATED("맛집을 수정하였습니다." , HttpStatus.OK),

    RESTAURANT_CREATED("맛집이 등록되었습니다." , HttpStatus.CREATED),
    RESTAURANT_FIND_ALL("맛집 리스트가 조회되었습니다.", HttpStatus.OK),

    RESTAURANT_REVIEW_CREATED("맛집 후기가 등록되었습니다.", HttpStatus.CREATED),
    RESTAURANT_REVIEW_FIND_ALL("맛집 후기가 조회되었습니다..", HttpStatus.OK),
    RESTAURANT_MY_REVIEW_FIND_ALL("나의 맛집 후기가 조회되었습니다..", HttpStatus.OK),

    RESTAURANT_SEARCH_FIND_FROM_OTHER_GROUP("다른 그룹에 대한 맛집을 조회하였습니다." , HttpStatus.OK),

    RESTAURANT_LOCATION_NOT_FOUND("맛집 위치정보가 등록되지 않았습니다." , HttpStatus.NOT_FOUND),
    RESTAURANT_LOCATION_FIND("맛집 위치 정보를 조회하였습니다." , HttpStatus.OK),
    RESTAURANT_SEARCH_FIND("맛집 위치 정보를 조회하였습니다." , HttpStatus.OK),
    DETAIL_RESTAURANT_FIND_SUCCESS("맛집 세부 정보를 조회하였습니다.", HttpStatus.OK),
    RECOMMEND_RESTAURANT_NOT_FOUND("맛집 세부 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LOCATION_PARSE_FAIL("요청하신 위치 정보가 잘못되었습니다.", HttpStatus.BAD_REQUEST),

    RESTAURANT_IMAGE_UPLOAD_FAIL("이미지 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String message;
    private final HttpStatus status;
}
