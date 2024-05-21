package com.gdsc.jmt.domain.user.dao;

import com.gdsc.jmt.domain.user.oauth.info.OAuth2UserInfo;
import com.gdsc.jmt.domain.user.repository.UserRepository;
import com.gdsc.jmt.global.jwt.dto.UserLoginAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserRepository userRepository;

    private void saveUser(OAuth2UserInfo userInfo) {
        if ( userRepository.findByEmail(userInfo.getEmail()).isEmpty()) {
            userRepository.save(userInfo.createUserEntity());
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
}
