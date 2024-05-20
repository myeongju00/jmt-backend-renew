package com.gdsc.jmt.domain.user.entity.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    APPLE("apple");
    private final String type;
}