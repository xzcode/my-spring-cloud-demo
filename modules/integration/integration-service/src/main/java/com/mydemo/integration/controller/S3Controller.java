package com.mydemo.integration.controller;

import com.mydemo.common.model.Result;
import com.mydemo.integration.common.dto.S3PresignRequest;
import com.mydemo.integration.common.dto.S3PresignResponse;
import com.mydemo.integration.service.S3PresignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "S3 文件上传")
@RestController
@RequestMapping("/api/integration/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3PresignService s3PresignService;

    @Operation(summary = "获取 S3 预签名上传 URL")
    @PostMapping("/presign")
    public Result<S3PresignResponse> presign(@RequestBody S3PresignRequest request) {
        return Result.ok(s3PresignService.generatePresignedUrl(
                request.getFileName(), request.getContentType(), request.getDirectory()));
    }
}
