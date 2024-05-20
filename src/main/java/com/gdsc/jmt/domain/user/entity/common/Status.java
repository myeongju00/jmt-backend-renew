package com.gdsc.jmt.domain.user.entity.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    ACTIVE("활동중"),
    SUSPEND("탈퇴");
    private final String status;
}
