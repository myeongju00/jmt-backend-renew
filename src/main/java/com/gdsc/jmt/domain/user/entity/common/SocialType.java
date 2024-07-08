package com.gdsc.jmt.domain.user.entity.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    APPLE("apple"),
    TEST("test");
    private final String type;
}