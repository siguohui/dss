package com.xiaosi.back.utils;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Component
public class MinioUtils {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Resource
    private MinioClient minioClient;

    public ApiResult uploadFile(MultipartFile file) throws AppException {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        try (InputStream fi = file.getInputStream()) {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).contentType(file.getContentType()).object(fileName).stream(fi, fi.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new AppException("文件上传失败" + e.getMessage());
        }
        return ApiResult.ok(fileName);
    }

    public ApiResult getPreviewUrl(String objectName) throws AppException {
        try {
            GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).method(Method.GET).build();
            return ApiResult.ok(minioClient.getPresignedObjectUrl(urlArgs));
        } catch (Exception e) {
            throw new AppException("获取预览链接失败" + e.getMessage());
        }
    }
}
