package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class S3PresignResponse implements Serializable {

    /** 预签名上传 URL */
    private String uploadUrl;

    /** 文件访问 URL */
    private String fileUrl;

    /** 过期时间（秒） */
    private Integer expireSeconds;
}
