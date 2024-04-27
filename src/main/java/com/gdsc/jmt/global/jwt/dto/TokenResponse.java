package com.gdsc.jmt.global.jwt.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private UserLoginAction userLoginAction;
    public TokenResponse( String grantType,String accessToken,String refreshToken,Long accessTokenExpiresIn){
        this.accessToken = accessToken;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        // 기본값
        this.userLoginAction = UserLoginAction.LOG_IN;
    }
}
