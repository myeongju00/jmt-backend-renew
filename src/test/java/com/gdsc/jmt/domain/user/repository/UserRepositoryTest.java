package com.gdsc.jmt.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.entity.common.Status;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.oauth.info.impl.AppleOAuth2UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private final UserRepository userRepository;

    private OAuth2UserInfo userInfo;
    private UserEntity user;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        userInfo = new AppleOAuth2UserInfo(
                "",
                "test@test.com"
        );

        user = userInfo.createUserEntity();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 저장 테스트")
    void save() {
        UserEntity savedUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userInfo.getEmail());
        assertThat(savedUser.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    @DisplayName("이메일로 사용자 조회 테스트")
    void findByEmail() {
        UserEntity findUser = userRepository.findByEmail(user.getEmail())
                .orElse(null);

        assertThat(findUser).isNotNull();
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("닉네임으로 사용자 조회 테스트")
    void findByNickname() {
        UserEntity findUser = userRepository.findByNickname(user.getNickname())
                .orElse(null);

        assertThat(findUser).isNotNull();
        assertThat(findUser.getNickname()).isEqualTo(user.getNickname());
    }
}
