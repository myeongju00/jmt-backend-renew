package com.gdsc.jmt.domain.user.service;

import com.gdsc.jmt.domain.user.dao.UserDao;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.messege.UserMessage;
import com.gdsc.jmt.global.service.S3FileService;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final S3FileService s3FileService;
    private final UserDao userDao;
    private final String DEFAULT_PROFILE_IMAGE_URL = "https://gdsc-jmt.s3.ap-northeast-2.amazonaws.com/profileImg/defaultImg/Default+image.png";

    @Transactional
    public void updateUserNickName(String email, String nickName) {
        if(userDao.isExistNickname(nickName)) {
            throw new ApiException(UserMessage.NICKNAME_IS_DUPLICATED);
        }
        userDao.updateNickname(email, nickName);
    }

    @Transactional
    public String updateUserProfileImg(String email, MultipartFile profileImg) {
        String responseUrl = uploadProfileImage(email, profileImg);
        updateProfileImage(email, responseUrl);
        return responseUrl;
    }

    @Transactional
    public String updateUserDefaultProfileImg(String email) {
        updateProfileImage(email, DEFAULT_PROFILE_IMAGE_URL);
        return DEFAULT_PROFILE_IMAGE_URL;
    }

    private String uploadProfileImage(String email, MultipartFile profileImg) {
        String responseUrl;
        try {
            String path = email + "/" + "profileImg";
            responseUrl = s3FileService.upload(profileImg,  path);
        } catch (IOException e) {
            throw new ApiException(UserMessage.PROFILE_IMAGE_UPLOAD_FAIL);
        }
        return responseUrl;
    }

    private void updateProfileImage(String email, String imageUrl) {

        String deleteImageUrl = userDao.updateProfileImage(email, imageUrl);

        if(deleteImageUrl != null) {
            s3FileService.delete(deleteImageUrl);
        }
    }
}