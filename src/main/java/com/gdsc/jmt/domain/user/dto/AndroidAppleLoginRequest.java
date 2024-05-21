package com.gdsc.jmt.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AndroidAppleLoginRequest(
        @Schema(description = "사용자 이메일 입니다.", example = "test@gmail.com")
        String email,
        @Schema(description = "안드로이드 측에서 Request가 왔다는 것을 검증하기 위한 Google Client ID 입니다.")
        String clientId
) { }
