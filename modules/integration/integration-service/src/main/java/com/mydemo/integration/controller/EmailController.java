package com.mydemo.integration.controller;

import com.mydemo.common.model.Result;
import com.mydemo.integration.common.dto.EmailRequest;
import com.mydemo.integration.common.dto.EmailVerifyRequest;
import com.mydemo.integration.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "邮箱验证码")
@RestController
@RequestMapping("/api/integration/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/send")
    public Result<Map<String, String>> send(@RequestBody EmailRequest request) {
        String code = emailService.sendVerifyCode(request.getEmail(), request.getBizType());
        return Result.ok(Map.of("message", "验证码已发送", "code_for_test", code));
    }

    @Operation(summary = "验证邮箱验证码")
    @PostMapping("/verify")
    public Result<Map<String, Boolean>> verify(@RequestBody EmailVerifyRequest request) {
        boolean success = emailService.verifyCode(request.getEmail(), request.getCode(), request.getBizType());
        return Result.ok(Map.of("verified", success));
    }
}
