package com.gdsc.jmt.domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProfileImgRequest(
        MultipartFile profileImg
) { }
