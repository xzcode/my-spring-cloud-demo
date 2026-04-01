package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WalletFeignClientFallbackFactory implements FallbackFactory<WalletFeignClient> {

    @Override
    public WalletFeignClient create(Throwable cause) {
        log.error("WalletFeignClient 调用失败", cause);
        return userId -> Result.fail("钱包服务暂时不可用");
    }
}
