package com.gdsc.jmt.global.service;

import static org.junit.jupiter.api.Assertions.*;

import com.gdsc.jmt.CustomSpringBootTest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CustomSpringBootTest
class S3FileServiceTest {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private S3FileService s3FileService;
    String uploadImgUrl;
    @Mock
    MultipartFile multipartFile = new MockMultipartFile("test.jpg", "test.jpg", "image/jpg", "test data".getBytes());

    @BeforeEach
    void setUp() throws IOException {
        uploadImgUrl = s3FileService.upload(multipartFile, "test");
    }

    @Test
    void delete() {
        s3FileService.delete(uploadImgUrl);

    }

    @Test
    @DisplayName("S3에 이미지를 업로드하면 정상적으로 업로드된다.")
    void upload() throws IOException {

        assertNotNull(uploadImgUrl);
        s3FileService.delete(uploadImgUrl);
    }
}