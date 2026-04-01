package com.mydemo.integration.service;

import com.mydemo.common.util.IdGenerator;
import com.mydemo.integration.common.dto.S3PresignResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class S3PresignService {

    @Value("${app.s3.endpoint:https://s3.amazonaws.com}")
    private String endpoint;

    @Value("${app.s3.bucket:mydemo-uploads}")
    private String bucket;

    @Value("${app.s3.presign-expire-seconds:3600}")
    private int presignExpireSeconds;

    /**
     * 获取 S3 预签名上传 URL（Mock 实现）
     * <p>实际项目请接入 AWS SDK 或 MinIO Client</p>
     */
    public S3PresignResponse generatePresignedUrl(String fileName, String contentType, String directory) {
        // 生成唯一文件路径: {directory}/{date}/{snowflakeId}_{originalName}
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uniqueFileName = IdGenerator.nextIdStr() + "_" + sanitizeFileName(fileName);
        String objectKey = directory + "/" + datePath + "/" + uniqueFileName;

        // TODO: 接入真实 S3/MinIO 生成预签名 URL
        String uploadUrl = endpoint + "/" + bucket + "/" + objectKey + "?X-Mock-Presign=true";
        String fileUrl = endpoint + "/" + bucket + "/" + objectKey;

        log.info("[S3 Mock] 生成预签名 URL: directory={}, fileName={}", directory, fileName);

        S3PresignResponse response = new S3PresignResponse();
        response.setUploadUrl(uploadUrl);
        response.setFileUrl(fileUrl);
        response.setExpireSeconds(presignExpireSeconds);
        return response;
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null) return "unnamed";
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
