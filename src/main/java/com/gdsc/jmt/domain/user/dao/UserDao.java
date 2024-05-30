package com.gdsc.jmt.domain.user.dao;

import com.gdsc.jmt.domain.user.entity.UserEntity;
import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.repository.UserRepository;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.dto.UserLoginAction;
import com.gdsc.jmt.global.messege.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserRepository userRepository;

    private void saveUser(OAuth2UserInfo userInfo) {
        if ( userRepository.findByEmail(userInfo.getEmail()).isEmpty()) {
            saveUser(userInfo.createUserEntity());
        };
    }

    public UserLoginAction signUpOrSignIn(OAuth2UserInfo userInfo) {
        if(userRepository.findByEmail(userInfo.getEmail()).isEmpty()) {
            saveUser(userInfo);
            return UserLoginAction.SIGN_UP;
        } else {
            return UserLoginAction.LOG_IN;
        }
    }

    public boolean isExistNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(UserMessage.USER_NOT_FOUND)
        );
    }

    public void updateNickname(String email, String nickname) {
        UserEntity user = findUserByEmail(email);

        user.updateNickname(nickname);
        saveUser(user);
    }

    public String updateProfileImage(String email, String imageUrl) {
        UserEntity user = findUserByEmail(email);
        String deleteImageUrl = user.getProfileImageUrl();

        user.updateProfileImageUrl(imageUrl);
        saveUser(user);

        return deleteImageUrl;
    }

    private void saveUser(UserEntity user) {
        userRepository.save(user);
    }
}
