package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class S3PresignRequest implements Serializable {

    /** 文件名 */
    private String fileName;

    /** 文件类型 (image/png, video/mp4 等) */
    private String contentType;

    /** 上传目录 (avatar, live-cover, chat-image 等) */
    private String directory;
}
