package com.gdsc.jmt.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.entity.common.RoleType;
import com.gdsc.jmt.domain.user.entity.common.SocialType;
import com.gdsc.jmt.domain.user.entity.common.Status;
import com.gdsc.jmt.domain.user.repository.UserRepository;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.service.S3FileService;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private S3FileService s3FileService;

    @InjectMocks
    private UserService userService;
    private final String email = "test@test.com";

    @BeforeEach
    void setUp() {
    }

    @DisplayName("닉네임 변경")
    @Test
    void updateUserNickName() {
        String newNickName = "newNickName";

        when(userDao.isExistNickname(newNickName)).thenReturn(false);
        when(userDao.updateNickname(email, newNickName)).thenReturn(newNickName);

        String result = userService.updateUserNickName(email, newNickName);

        assertEquals(newNickName, result);

        verify(userDao, times(1)).isExistNickname(newNickName);
        verify(userDao, times(1)).updateNickname(email, newNickName);
    }

    @Test
    @DisplayName("중복된 닉네임으로 변경시  ApiException 발생")
    void updateDuplicatedNickName() {

        String nickName = "nickName";

        when(userDao.isExistNickname(nickName)).thenReturn(true);

        assertThrows(ApiException.class, () -> {
            userService.updateUserNickName(email, nickName);
        });

    }

    @Test
    @DisplayName("닉네임 중복 확인")
    void requestNickNameisDuplicate() {
        String nickName = "nickName";

        when(userDao.isExistNickname(nickName)).thenReturn(false);

        assertDoesNotThrow(() -> {
            userService.checkDuplicateUserNickname(nickName);
        });
    }

    @Test
    @DisplayName("중복 닉네임 시 ApiException 발생")
    void requestDuplicatedNickNameisDuplicate() {
        String nickName = "nickName";

        when(userDao.isExistNickname(nickName)).thenReturn(true);

        assertThrows(ApiException.class, () -> {
            userService.checkDuplicateUserNickname(nickName);
        });

    }


    @Test
    @DisplayName("프로필 이미지 변경")
    void updateUserProfileImg() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(s3FileService.upload(multipartFile, email + "/profileImg")).thenReturn("testUrl");

        String responseUrl = userService.updateUserProfileImg(email, multipartFile);

        assertEquals("testUrl", responseUrl);
    }

    @Test
    @DisplayName("기본 프로필 이미지로 변경")
    void updateUserDefaultProfileImg() {
        when(userDao.updateProfileImage(email, "testUrl")).thenReturn("testUrl");

//        userService.updateUserDefaultProfileImg(email);

        verify(s3FileService, times(1)).delete("testUrl");

    }
}