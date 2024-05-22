package com.gdsc.jmt.domain.user.controller;

import com.gdsc.jmt.domain.user.controller.springdocs.GoogleLoginSpringDocs;
import com.gdsc.jmt.domain.user.controller.springdocs.LogoutSpringDocs;
import com.gdsc.jmt.domain.user.controller.springdocs.ReissueSpringDocs;
import com.gdsc.jmt.domain.user.dto.LogoutRequest;
import com.gdsc.jmt.domain.user.dto.ReissueRequest;
import com.gdsc.jmt.domain.user.dto.SocialLoginRequest;
import com.gdsc.jmt.domain.user.service.AuthService;
import com.gdsc.jmt.global.controller.FirstVersionRestController;
import com.gdsc.jmt.global.dto.JMTApiResponse;
import com.gdsc.jmt.global.jwt.dto.TokenResponse;
import com.gdsc.jmt.global.jwt.dto.UserInfo;
import com.gdsc.jmt.global.messege.UserMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자 인증 관련 컨트롤러")
@FirstVersionRestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/google")
    @GoogleLoginSpringDocs
    public JMTApiResponse<TokenResponse> googleLogin(@RequestBody SocialLoginRequest socialLoginRequest) {
        TokenResponse tokenResponse = authService.googleLogin(socialLoginRequest.token());
        return JMTApiResponse.createResponseWithMessage(tokenResponse, UserMessage.LOGIN_SUCCESS);
    }

    @PostMapping("/token")
    @ReissueSpringDocs
    public JMTApiResponse<TokenResponse> reissue(@RequestBody LogoutRequest logoutRequest) {
        TokenResponse tokenResponse = authService.reissue(logoutRequest.accessToken(), logoutRequest.refreshToken());
        return JMTApiResponse.createResponseWithMessage(tokenResponse, UserMessage.REISSUE_SUCCESS);
    }

    @DeleteMapping("/auth/user")
    @LogoutSpringDocs
    public JMTApiResponse<?> logout(@AuthenticationPrincipal UserInfo user, @RequestBody ReissueRequest reissueRequest) {
        authService.logout(user.getEmail(), user.getProvider(), reissueRequest.refreshToken());
        return JMTApiResponse.createResponseWithMessage("", UserMessage.LOGOUT_SUCCESS);
    }
}
