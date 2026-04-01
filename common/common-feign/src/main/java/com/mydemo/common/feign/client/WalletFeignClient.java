package com.mydemo.common.feign.client;

import com.mydemo.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 钱包服务 Feign Client
 */
@FeignClient(name = "service-wallet", fallbackFactory = WalletFeignClientFallbackFactory.class)
public interface WalletFeignClient {

    @GetMapping("/api/wallet/{userId}")
    Result<Map<String, Object>> getWallet(@PathVariable("userId") String userId);
}
