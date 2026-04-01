package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 直播服务 Feign Client
 */
@FeignClient(name = "service-live", fallbackFactory = LiveFeignClientFallbackFactory.class)
public interface LiveFeignClient {

    @GetMapping("/api/live/room/{roomId}")
    Result<Map<String, Object>> getRoomById(@PathVariable("roomId") String roomId);
}
