package com.gdsc.jmt.domain.user.service;

import static com.gdsc.jmt.global.messege.AuthMessage.GOOGLE_INVALID_ID_TOKEN;

import com.gdsc.jmt.domain.user.apple.AppleUtil;
import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.oauth.info.impl.AppleOAuth2UserInfo;
import com.gdsc.jmt.domain.user.oauth.info.impl.GoogleOAuth2UserInfo;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.TokenProvider;
import com.gdsc.jmt.global.jwt.dto.TokenResponse;
import com.gdsc.jmt.global.jwt.dto.UserLoginAction;
import com.gdsc.jmt.global.messege.UserMessage;
import com.gdsc.jmt.global.service.RedisService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${apple.side.google.client.id}")
    private String appleSideGoogleClientId;

    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final UserDao userDao;
    private final AppleUtil appleUtil;

    @Transactional
    public TokenResponse googleLogin(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(List.of(googleClientId, appleSideGoogleClientId))
                .build();

        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);

            if (googleIdToken == null) {
                throw new ApiException(GOOGLE_INVALID_ID_TOKEN);
            }
            else {
                GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(googleIdToken.getPayload());
                String provider = String.valueOf(userInfo.getSocialType());
                TokenResponse tokenResponse = generateJwtToken(provider, userInfo.getEmail());

                UserLoginAction loginAction = userDao.signUpOrSignIn(userInfo);
                tokenResponse.updateLoginActionFlag(loginAction);

                return generateJwtToken(provider, userInfo.getEmail());
            }
        } catch (IllegalArgumentException | HttpClientErrorException | GeneralSecurityException | IOException e) {
            throw new ApiException(GOOGLE_INVALID_ID_TOKEN);
        }
    }

    @Transactional
    public TokenResponse appleLogin(String idToken) {
        OAuth2UserInfo userInfo = appleUtil.getAppleUserInfo(idToken);
        String provider = String.valueOf(userInfo.getSocialType());
        UserLoginAction action = userDao.signUpOrSignIn(userInfo);

        TokenResponse tokenResponse = generateJwtToken(provider, userInfo.getEmail());
        tokenResponse.updateLoginActionFlag(action);
        return tokenResponse;
    }

//    @Transactional
//    public TokenResponse testLogin(String email) {
//        saveUser(new TestOAuth2UserInfo());
//        return generateJwtToken(String.valueOf(SocialType.TEST), email);
//    }

    @Transactional
    public TokenResponse loginForTest(String email) {
        OAuth2UserInfo userInfo = new AppleOAuth2UserInfo("test", email);
        UserLoginAction action = userDao.signUpOrSignIn(userInfo);
        TokenResponse tokenResponse = generateJwtToken("test", userInfo.getEmail());
        tokenResponse.updateLoginActionFlag(action);
        return tokenResponse;
    }

    @Transactional
    public void logout(String email, String provider, String requestRefreshToken) {
        validateRefreshToken(requestRefreshToken);

        String refreshTokenInRedis = getRedisServiceValues(String.valueOf(provider), email);

        if(refreshTokenInRedis == null || !refreshTokenInRedis.equals(requestRefreshToken)) {
            throw new ApiException(UserMessage.REFRESH_TOKEN_INVALID);
        }

        // Redis에 저장되어 있는 RT 삭제
        deleteRedisValue(String.valueOf(provider), email);
    }

    // 토큰 재발급: validate 메서드가 true 반환할 때만 사용 -> AT, RT 재발급
    @Transactional
    public TokenResponse reissue(String accessToken, String refreshToken) {

        validateRefreshToken(refreshToken);
        validateAccessToken(accessToken); // AT 유효성 검사

        String provider = tokenProvider.getSocialType(accessToken);
        String email = tokenProvider.getEmail(accessToken);
        String refreshTokenInRedis = getRedisServiceValues(provider, email);

        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if(!isValidateToken(refreshToken) || !refreshTokenInRedis.equals(refreshToken)) {
            deleteRedisValue(provider, email); // 탈취 가능성 -> 삭제
            return null; // -> 재로그인 요청
        }

        // 토큰 재발급 및 Redis 업데이트
        deleteRedisValue(provider, email); // 기존 RT 삭제
        return generateJwtToken(provider, email);
    }

    private boolean isValidateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    private String getRedisServiceValues(String provider, String email) {
        return redisService.getValues("RT(" + provider + "):" + email);
    }

    private void deleteRedisValue(String provider, String email) {
        redisService.deleteValues("RT(" + provider + "):" + email);
    }

    private TokenResponse generateJwtToken(String provider, String email) {
        // RT가 이미 있을 경우
        if(getRedisServiceValues(provider, email) != null) {
            deleteRedisValue(provider, email); // 삭제
        }
        // AT, RT 생성 및 Redis에 RT 저장
        TokenResponse tokenDto = createJwtToken(provider, email);
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Transactional
    public void saveRefreshToken(String provider, String email, String refreshToken) {
        redisService.setValues("RT(" + provider + "):" + email, // key
                refreshToken, // value
                tokenProvider.getTokenExpirationTime(refreshToken)); // timeout(milliseconds)
    }

    private TokenResponse createJwtToken(String provider, String email) {
        return tokenProvider.generateJwtToken(provider, email, RoleType.MEMBER);
    }

    private void validateRefreshToken(String refreshToken) {
        if(!isValidateToken(refreshToken))
            throw new ApiException(UserMessage.REFRESH_TOKEN_INVALID);
    }

    private void validateAccessToken(String accessToken) {
        if(!isValidateToken(accessToken))
            throw new ApiException(UserMessage.ACCESS_TOKEN_INVALID);
    }
}
