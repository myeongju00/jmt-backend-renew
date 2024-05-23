package com.gdsc.jmt.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.TokenProvider;
import com.gdsc.jmt.global.jwt.dto.TokenResponse;
import com.gdsc.jmt.global.messege.UserMessage;
import com.gdsc.jmt.global.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.LocalDateAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    RedisService redisService;
    private final String secretKey = "jaeuhfjskfehesuhvguedhvjksbvnebhfhrueshfjkebdjknvbsjkeb";
    private final TokenProvider tokenProvider
            = new TokenProvider(
            secretKey
    );
    private final String testProvider = "test";
    private final String testEmail = "test@test.com";

    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        tokenResponse = authService.loginForTest(testEmail);
    }

    @AfterEach
    void tearDown() {
        redisService.deleteValues(("RT(" + testProvider + "):" + testEmail));
    }

    @Test
    void googleLogin() {

    }

    @Test
    @DisplayName("로그아웃 시 레디스에 저장된 RefreshToken이 삭제되는지 확인")
    void logout() {
        authService.logout(testEmail, testProvider, tokenResponse.getRefreshToken());

        assertThat(redisService.getValues(("RT(" + testProvider + "):" + testEmail)))
                .isEqualTo("false");
    }

    @Test
    void reissueByInvalidAccessToken() {
        String invalidAccessToken = "invalidAccessToken";

        assertThatThrownBy(() -> authService.reissue(invalidAccessToken, tokenResponse.getRefreshToken()))
                .isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("토큰 재발급시 새로운 토큰이 발급되는지 확인")
    void reissue() {
        TokenResponse reissueTokenResponse = authService.reissue(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        assertThat(reissueTokenResponse.getAccessToken()).isNotEmpty();
        assertThat(reissueTokenResponse.getRefreshToken()).isNotEmpty();
    }

    @Test
    @DisplayName("RefreshToken이 레디스에 잘 저장되었는지 확인")
    void saveRefreshToken() {
        authService.saveRefreshToken(testProvider, testEmail, tokenResponse.getRefreshToken());

        assertThat(tokenResponse.getRefreshToken())
                .isEqualTo(redisService.getValues(("RT(" + testProvider + "):" + testEmail)));
    }
}