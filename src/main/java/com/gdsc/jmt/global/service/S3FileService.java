package com.gdsc.jmt.global.service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3FileService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    private static String getUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void delete(String path) {
        DeleteObjectRequest request = getDeleteObjectRequest(path);
        if(request == null) {
            return;
        }
        s3Client.deleteObject(request);
    }

    private DeleteObjectRequest getDeleteObjectRequest(String path) {
        String key = path.substring(path.lastIndexOf("com/") + 4);
        if(key.equals("profileImg/defaultImg/Default+image.png")) {
            return null;
        }
        return DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
    }

    public String upload(MultipartFile multipartFile, String path) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        //확장자 추출
        String ext = originName.substring(originName.lastIndexOf(".") + 1);

        //중복 방지를 위해 파일명에 UUID 추가
        String storeFileName = getUUid() + "." + ext;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String key = path + "/" + date + "/" + storeFileName;
        return this.putS3(multipartFile, key);
    }

    private String putS3(MultipartFile file, String key) throws IOException {
        PutObjectRequest objectRequest = getPutObjectRequest(key, file.getContentType());
        RequestBody requestBody = getFileRequestBody(file);
        s3Client.putObject(objectRequest, requestBody);

        return findUploadKeyUrl(key);
    }

    // 파일 업로드를 하기위한 PutObjectRequest를 반환
    private PutObjectRequest getPutObjectRequest(String key, String contentType) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .contentType(contentType)
                .key(key)
                .build();
    }

    // MultipartFile을 업로드 하기위해 RequestBody.fromInputStream에 InputStream과 file의 Size 넣기
    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }

    // S3Utilities를 통해 GetUrlRequest를 파라미터로 넣어 파라미터로 넘어온 key의 접근 경로를 URL로 반환
    private String findUploadKeyUrl (String key) {
        S3Utilities s3Utilities = s3Client.utilities();
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        URL url = s3Utilities.getUrl(request);
        return url.toString();
    }
}