package com.mydemo.integration.controller;

import com.mydemo.common.model.Result;
import com.mydemo.integration.common.dto.SmsRequest;
import com.mydemo.integration.common.dto.SmsVerifyRequest;
import com.mydemo.integration.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "短信验证码")
@RestController
@RequestMapping("/api/integration/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @Operation(summary = "发送短信验证码")
    @PostMapping("/send")
    public Result<Map<String, String>> send(@RequestBody SmsRequest request) {
        String code = smsService.sendVerifyCode(request.getPhone(), request.getBizType());
        // 开发环境返回验证码方便测试，生产环境不返回
        return Result.ok(Map.of("message", "验证码已发送", "code_for_test", code));
    }

    @Operation(summary = "验证短信验证码")
    @PostMapping("/verify")
    public Result<Map<String, Boolean>> verify(@RequestBody SmsVerifyRequest request) {
        boolean success = smsService.verifyCode(request.getPhone(), request.getCode(), request.getBizType());
        return Result.ok(Map.of("verified", success));
    }
}
