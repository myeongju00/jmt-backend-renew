package com.gdsc.jmt.domain.user.dto.response;


import com.gdsc.jmt.domain.user.entity.UserEntity;

public record UserResponse(
        Long id,
        String email,
        String nickname,
        String profileImg ) {
    public UserResponse(UserEntity userEntity) {
        this(userEntity.getId(),
             userEntity.getEmail(),
             userEntity.getNickname(),
             userEntity.getProfileImageUrl()
        );
    }
}
