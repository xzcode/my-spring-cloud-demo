package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class LiveFeignClientFallbackFactory implements FallbackFactory<LiveFeignClient> {

    @Override
    public LiveFeignClient create(Throwable cause) {
        log.error("LiveFeignClient 调用失败", cause);
        return roomId -> Result.fail("直播服务暂时不可用");
    }
}
