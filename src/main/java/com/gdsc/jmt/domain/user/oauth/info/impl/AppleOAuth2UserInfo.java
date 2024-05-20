package com.gdsc.jmt.domain.user.oauth.info.impl;

import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import lombok.Getter;

@Getter
public class AppleOAuth2UserInfo  extends OAuth2UserInfo {
    private String id;
    private String name;
    private String email;
    private String imageUrl;

    private final SocialType socialType = SocialType.APPLE;

    public AppleOAuth2UserInfo(String id, String email) {
        this.id = id;
        this.name = "비었어요 엉엉엉어엉엉엉";
        this.email = email;
        this.imageUrl = "default image";
    }

}
