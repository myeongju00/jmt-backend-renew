package com.gdsc.jmt.domain.user.oauth.info.impl;

import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.Getter;

@Getter
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    private String id;
    private String name;
    private String email;
    private String imageUrl;

    private final SocialType socialType = SocialType.GOOGLE;

    public GoogleOAuth2UserInfo(GoogleIdToken.Payload payload) {
        this.id = payload.getSubject();
        this.email = payload.getEmail();
        this.name = (String) payload.get("name");
        this.imageUrl = (String) payload.get("picture");
    }


}
