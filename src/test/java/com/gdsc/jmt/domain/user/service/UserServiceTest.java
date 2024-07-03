package com.gdsc.jmt.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.global.service.S3FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private S3FileService s3FileService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("닉네임 업데이트 - 성공")
    @Test
    void updateUserNickName_Success() {
        String email = "test@example.com";
        String newNickName = "newNickName";

        when(userDao.isExistNickname(newNickName)).thenReturn(false);
        when(userDao.updateNickname(email, newNickName)).thenReturn(newNickName);

        String result = userService.updateUserNickName(email, newNickName);

        assertEquals(newNickName, result);
        verify(userDao, times(1)).isExistNickname(newNickName);
        verify(userDao, times(1)).updateNickname(email, newNickName);
    }


    @Test
    void updateUserProfileImg() {
    }

    @Test
    void updateUserDefaultProfileImg() {
    }
}