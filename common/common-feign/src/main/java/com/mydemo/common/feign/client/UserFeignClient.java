package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 用户服务 Feign Client
 */
@FeignClient(name = "service-user", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @GetMapping("/api/user/{userId}")
    Result<Map<String, Object>> getUserById(@PathVariable("userId") String userId);
}
