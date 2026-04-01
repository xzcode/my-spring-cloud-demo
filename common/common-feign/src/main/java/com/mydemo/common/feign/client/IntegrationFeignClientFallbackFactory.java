package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class IntegrationFeignClientFallbackFactory implements FallbackFactory<IntegrationFeignClient> {

    @Override
    public IntegrationFeignClient create(Throwable cause) {
        log.error("IntegrationFeignClient 调用失败", cause);
        return new IntegrationFeignClient() {
            @Override
            public Result<Map<String, String>> sendSms(Map<String, String> request) {
                return Result.fail("集成服务暂时不可用");
            }

            @Override
            public Result<Map<String, String>> sendEmail(Map<String, String> request) {
                return Result.fail("集成服务暂时不可用");
            }
        };
    }
}
