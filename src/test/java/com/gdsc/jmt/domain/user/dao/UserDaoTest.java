package com.gdsc.jmt.domain.user.dao;

import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.entity.common.Status;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.repository.UserRepository;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.dto.UserLoginAction;
import com.gdsc.jmt.global.messege.UserMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDao userDao;

    private UserEntity userEntity;
    private OAuth2UserInfo userInfo;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .email("test@example.com")
                .socialType(SocialType.GOOGLE)
                .roleType(RoleType.MEMBER)
                .status(Status.ACTIVE)
                .build();
        userEntity.updateProfileImageUrl("testImageUrl");
        userInfo = mock(OAuth2UserInfo.class);
    }

    @Test
    void testSignUpOrSignIn_SignUp() {
        when(userInfo.getEmail()).thenReturn("test@example.com");
        when(userInfo.createUserEntity()).thenReturn(userEntity);
        when(userRepository.findByEmail(userInfo.getEmail())).thenReturn(Optional.empty());

        UserLoginAction action = userDao.signUpOrSignIn(userInfo);

        assertEquals(UserLoginAction.SIGN_UP, action);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testSignUpOrSignIn_LogIn() {
        when(userRepository.findByEmail(userInfo.getEmail())).thenReturn(Optional.of(userEntity));

        UserLoginAction action = userDao.signUpOrSignIn(userInfo);

        assertEquals(UserLoginAction.LOG_IN, action);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testIsExistNickname() {
        when(userRepository.findByNickname("testNickname")).thenReturn(Optional.of(userEntity));

        boolean exists = userDao.isExistNickname("testNickname");

        assertTrue(exists);
    }

    @Test
    void testFindUserByEmail_UserExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));

        UserEntity foundUser = userDao.findUserByEmail("test@example.com");

        assertEquals(userEntity, foundUser);
    }

    @Test
    void testFindUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            userDao.findUserByEmail("test@example.com");
        });

        assertEquals(UserMessage.USER_NOT_FOUND, exception.getResponseMessage());
    }

    @Test
    void testUpdateNickname() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));

        String newNickname = "newNickname";
        String returnedNickname = userDao.updateNickname("test@example.com", newNickname);

        assertEquals(newNickname, returnedNickname);
        assertEquals(newNickname, userEntity.getNickname());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateProfileImage() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));

        String newImageUrl = "newImageUrl";
        String oldImageUrl = userDao.updateProfileImage("test@example.com", newImageUrl);

        assertEquals("testImageUrl", oldImageUrl);
        assertEquals(newImageUrl, userEntity.getProfileImageUrl());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testSaveTestUser() {
        userDao.saveTestUser(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }
}
