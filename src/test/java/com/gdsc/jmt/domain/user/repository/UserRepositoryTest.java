package com.gdsc.jmt.domain.user.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //  임베디드 데이터 베이스를 사용 안한다는 선언.
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private OAuth2UserInfo userInfo;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        userInfo = new AppleOAuth2UserInfo(
                "",
                "test@test.com"
        );

        user = userRepository.save(userInfo.createUserEntity());
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

    @Test
    @DisplayName("사용자 저장 테스트")
    void save() {
        assertThat(user.getEmail()).isEqualTo(userInfo.getEmail());
        assertThat(user.getStatus()).isEqualTo(Status.ACTIVE);
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