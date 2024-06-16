package com.gdsc.jmt.domain.user.controller;

import com.gdsc.jmt.domain.user.controller.springdocs.UpdateDefaultProfileImgSpringDocs;
import com.gdsc.jmt.domain.user.controller.springdocs.UpdateUserNicknameSpringDocs;
import com.gdsc.jmt.domain.user.controller.springdocs.UpdateUserProfileImgSpringDocs;
import com.gdsc.jmt.domain.user.dto.NicknameRequest;
import com.gdsc.jmt.domain.user.dto.ProfileImgRequest;
import com.gdsc.jmt.domain.user.dto.response.UserNicknameResponse;
import com.gdsc.jmt.domain.user.service.UserService;
import com.gdsc.jmt.global.controller.FirstVersionRestController;
import com.gdsc.jmt.global.dto.JMTApiResponse;
import com.gdsc.jmt.global.exception.ApiException;
import com.gdsc.jmt.global.jwt.dto.UserInfo;
import com.gdsc.jmt.global.messege.UserMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사용자 정보 관련 컨트롤러")
@FirstVersionRestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/nickname")
    @UpdateUserNicknameSpringDocs
    public JMTApiResponse<UserNicknameResponse> updateUserNickname(@AuthenticationPrincipal UserInfo user, @RequestBody NicknameRequest nicknameRequest) {
        String nickname = userService.updateUserNickName(user.getEmail(), nicknameRequest.nickname());
        UserNicknameResponse response = new UserNicknameResponse(user.getEmail(), nickname);
        return JMTApiResponse.createResponseWithMessage(response, UserMessage.NICKNAME_UPDATE_SUCCESS);
    }

    @PostMapping(value = "/user/profileImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UpdateUserProfileImgSpringDocs
    public JMTApiResponse<String> updateUserProfileImg(@AuthenticationPrincipal UserInfo user,
                                                  @ModelAttribute ProfileImgRequest profileImgRequest) {

        validateProfileImg(profileImgRequest.profileImg());

        String responseUrl = userService.updateUserProfileImg(user.getEmail(), profileImgRequest.profileImg());
        return JMTApiResponse.createResponseWithMessage(responseUrl, UserMessage.PROFILE_IMAGE_UPDATE_SUCCESS);
    }

    private void validateProfileImg(MultipartFile profileImg) {
        if(profileImg == null) {
            throw new ApiException(UserMessage.PROFILE_IMAGE_NOT_FOUND);
        }

        String contentType = profileImg.getContentType();
        if (contentType == null || !isImageContentType(contentType)) {
            throw new ApiException(UserMessage.PROFILE_CONTENT_TYPE_ERROR);
        }
    }

    private boolean isImageContentType(String contentType) {
        return contentType.toLowerCase().startsWith("image/");
    }

    @PostMapping(value = "/user/defaultProfileImg")
    @UpdateDefaultProfileImgSpringDocs
    public JMTApiResponse<?> updateUserDefaultProfileImg(@AuthenticationPrincipal UserInfo user) {
        String responseUrl = userService.updateUserDefaultProfileImg(user.getEmail());
        return JMTApiResponse.createResponseWithMessage(responseUrl, UserMessage.PROFILE_IMAGE_UPDATE_SUCCESS);
    }
}
