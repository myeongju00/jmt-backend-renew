package com.gdsc.jmt.domain.user.service;

import static com.gdsc.jmt.global.messege.AuthMessage.GOOGLE_INVALID_ID_TOKEN;

import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.domain.user.dto.LogoutRequest;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
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

                TokenResponse tokenResponse = generateJwtToken(userInfo.getSocialType(), userInfo.getEmail());

                UserLoginAction loginAction = userDao.signUpOrSignIn(userInfo);
                tokenResponse.updateLoginActionFlag(loginAction);

                return generateJwtToken(userInfo.getSocialType(), userInfo.getEmail());
            }
        } catch (IllegalArgumentException | HttpClientErrorException | GeneralSecurityException | IOException e) {
            throw new ApiException(GOOGLE_INVALID_ID_TOKEN);
        }
    }

//    @Transactional
//    public TokenResponse testLogin(String email) {
//        saveUser(new TestOAuth2UserInfo());
//        return generateJwtToken(String.valueOf(SocialType.TEST), email);
//    }

    @Transactional
    public void logout(String email, SocialType provider, String requestRefreshToken) {
        validateRefreshToken(requestRefreshToken);

        String refreshTokenInRedis = redisService.getValues("RT(" + provider + "):" + email);

        if(refreshTokenInRedis == null || !refreshTokenInRedis.equals(requestRefreshToken)) {
            throw new ApiException(UserMessage.REFRESH_TOKEN_INVALID);
        }

        // Redis에 저장되어 있는 RT 삭제
        redisService.deleteValues("RT(" + provider + "):" + email);
    }

    // 토큰 재발급: validate 메서드가 true 반환할 때만 사용 -> AT, RT 재발급
    @Transactional
    public TokenResponse reissue(LogoutRequest logoutRequest) {
        String accessToken = logoutRequest.accessToken();
        String requestRefreshToken = logoutRequest.refreshToken();

        validateRefreshToken(requestRefreshToken);
        validateAccessToken(accessToken); // AT 유효성 검사

        SocialType provider = tokenProvider.getSocialType(accessToken);
        String email = tokenProvider.getEmail(accessToken);

        String refreshTokenInRedis = redisService.getValues("RT(" + provider + "):" + email);

        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if(!tokenProvider.validateToken(requestRefreshToken) || !refreshTokenInRedis.equals(requestRefreshToken)) {
            redisService.deleteValues("RT(" + provider + "):" + email); // 탈취 가능성 -> 삭제
            return null; // -> 재로그인 요청
        }

        // 토큰 재발급 및 Redis 업데이트
        redisService.deleteValues("RT(" + provider + "):" + email); // 기존 RT 삭제
        return generateJwtToken(provider, email);
    }


    private TokenResponse generateJwtToken(SocialType provider, String email) {
        // RT가 이미 있을 경우
        if(redisService.getValues("RT(" + provider + "):" + email) != null) {
            redisService.deleteValues("RT(" + provider + "):" + email); // 삭제
        }
        // AT, RT 생성 및 Redis에 RT 저장
        TokenResponse tokenDto = createJwtToken(provider, email);
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Transactional
    public void saveRefreshToken(SocialType provider, String email, String refreshToken) {
        redisService.setValues("RT(" + provider + "):" + email, // key
                refreshToken, // value
                tokenProvider.getTokenExpirationTime(refreshToken)); // timeout(milliseconds)
    }

    private TokenResponse createJwtToken(SocialType provider, String email) {
        return tokenProvider.generateJwtToken(provider, email, RoleType.MEMBER);
    }

    private void validateRefreshToken(String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken))
            throw new ApiException(UserMessage.REFRESH_TOKEN_INVALID);
    }

    private void validateAccessToken(String accessToken) {
        if(!tokenProvider.validateToken(accessToken))
            throw new ApiException(UserMessage.ACCESS_TOKEN_INVALID);
    }
}
