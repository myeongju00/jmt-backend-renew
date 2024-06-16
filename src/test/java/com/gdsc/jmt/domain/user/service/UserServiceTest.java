package com.gdsc.jmt.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.entity.common.Status;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.oauth.info.impl.AppleOAuth2UserInfo;
import com.gdsc.jmt.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserDao userDao;

    @Mock
    private UserRepository userRepository;

    private final String email = "test@test.com";
    private final String nickname = "testNickname";
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(userRepository);
        userEntity = UserEntity.builder()
                .email(email)
                .socialType(SocialType.TEST)
                .roleType(RoleType.MEMBER)
                .status(Status.ACTIVE)
                .build();
        userEntity.updateNickname(nickname);

        userDao.saveTestUser(userEntity);
    }

    @Test
    void updateUserNickName() {
        String changedNickName = "changedNickName";
//
//        String expected = userDao.updateNickname(email, changedNickName);
//
//        assertNotNull(expected);
//        assertNotEquals(expected, nickname);
//        assertEquals(expected, changedNickName);
//        when(userDao.saveTestUser(userEntity)).thenReturn(1L);

        doReturn(changedNickName).when(userDao.updateNickname(anyString(), changedNickName));
    }

    @Test
    void updateUserProfileImg() {
    }

    @Test
    void updateUserDefaultProfileImg() {
    }
}