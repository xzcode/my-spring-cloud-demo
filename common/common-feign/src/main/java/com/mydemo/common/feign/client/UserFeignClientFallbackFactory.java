package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户服务 Feign 降级工厂
 */
@Slf4j
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable cause) {
        log.error("UserFeignClient 调用失败", cause);
        return new UserFeignClient() {
            @Override
            public Result<Map<String, Object>> getUserById(String userId) {
                return Result.fail("用户服务暂时不可用");
            }
        };
    }
}
