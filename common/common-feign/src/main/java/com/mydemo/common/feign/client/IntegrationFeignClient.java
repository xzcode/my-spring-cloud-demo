package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 集成服务 Feign Client
 */
@FeignClient(name = "service-integration", fallbackFactory = IntegrationFeignClientFallbackFactory.class)
public interface IntegrationFeignClient {

    @PostMapping("/api/integration/sms/send")
    Result<Map<String, String>> sendSms(@RequestBody Map<String, String> request);

    @PostMapping("/api/integration/email/send")
    Result<Map<String, String>> sendEmail(@RequestBody Map<String, String> request);
}
