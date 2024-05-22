package com.gdsc.jmt.global;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("dev")
public class JwtTokenProviderTest {
    @Autowired
    private TokenProvider jwtTokenProvider;
    private final String secretKey = "jaeuhfjskfehesuhvguedhvjksbvnebhfhrueshfjkebdjknvbsjkeb";

    private final TokenProvider invalidSecretKeyJwtTokenProvider
            = new TokenProvider(
            secretKey
    );

    @Test
    @DisplayName("토큰이 올바르게 생성된다.")
        // 5
    void createToken() {
        final long now = (new Date()).getTime();
        final Map<String, Object> payload = Map.of("email", "test", "role", "USER");

        final String accessToken = jwtTokenProvider.createAccessToken(now, payload);
        final String refreshToken = jwtTokenProvider.createRefreshToken(now);

        assertThat(accessToken).isNotNull();
        assertThat(refreshToken).isNotNull();
    }

    @DisplayName("올바른 토큰 정보로 payload를 조회한다.")    // 6
    @Test
    void getPayloadByValidToken() {
        final long now = (new Date()).getTime();
        final Map<String, Object> payload = Map.of("email", "test",
                "role", "USER");

        final String token = jwtTokenProvider.createAccessToken(now, payload);

        assertThat(jwtTokenProvider.getEmail(token)).isEqualTo(payload.get("email"));
    }

    @DisplayName("유효하지 않은 토큰 형식의 토큰인 경우 예외를 발생시킨다.")    // 7
    @Test
    void getPayloadByInvalidToken() {
        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> jwtTokenProvider.validateToken(null));
    }

    @DisplayName("만료된 토큰으로 payload를 조회할 경우 예외를 발생시킨다.")
    @Test
    void getPayloadByExpiredToken() {
        final String expiredToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .expiration(new Date((new Date()).getTime() - 1))    // 8
                .compact();

        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> jwtTokenProvider.validateToken(expiredToken));
    }

    @DisplayName("시크릿 키가 틀린 토큰 정보로 payload를 조회할 경우 예외를 발생시킨다.")
    @Test
    void getPayloadByWrongSecretKeyToken() {
        long now = (new Date()).getTime();
        final String invalidSecretToken = invalidSecretKeyJwtTokenProvider.createAccessToken(
                now,
                Map.of("email", "test"));

        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> jwtTokenProvider.validateToken(invalidSecretToken));
    }
}

