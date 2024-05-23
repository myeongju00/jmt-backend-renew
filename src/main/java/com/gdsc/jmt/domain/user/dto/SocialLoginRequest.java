package com.gdsc.jmt.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SocialLoginRequest(
        @Schema(description = "클라이언트에서 발급된 idToken입니다.", example = "idToken")
        String token
) { }
