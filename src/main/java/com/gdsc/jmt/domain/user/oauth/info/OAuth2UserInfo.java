package com.gdsc.jmt.domain.user.oauth.info;

import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.entity.common.Status;

public abstract class OAuth2UserInfo {
    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract SocialType getSocialType();


    public UserEntity createUserEntity() {
        return UserEntity.builder()
                .email(getEmail())
                .socialType(getSocialType())
                .roleType(RoleType.MEMBER)
                .status(Status.ACTIVE)
                .build();
    }
}
