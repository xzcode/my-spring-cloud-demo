package com.mydemo.integration.service;

import com.mydemo.integration.common.IntegrationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 发送短信验证码（Mock 实现，实际接入短信平台如阿里云 SMS）
     */
    public String sendVerifyCode(String phone, String bizType) {
        String code = generateCode();
        String key = IntegrationConstants.REDIS_VERIFY_CODE_PREFIX + bizType + ":" + phone;

        // 存入 Redis，设置过期时间
        stringRedisTemplate.opsForValue().set(key, code,
                IntegrationConstants.VERIFY_CODE_EXPIRE_SECONDS, TimeUnit.SECONDS);

        // TODO: 接入真实短信平台发送
        log.info("[SMS Mock] 向 {} 发送验证码: {}，业务类型: {}", phone, code, bizType);
        return code;
    }

    /**
     * 验证短信验证码
     */
    public boolean verifyCode(String phone, String code, String bizType) {
        String key = IntegrationConstants.REDIS_VERIFY_CODE_PREFIX + bizType + ":" + phone;
        String cachedCode = stringRedisTemplate.opsForValue().get(key);
        if (cachedCode != null && cachedCode.equals(code)) {
            stringRedisTemplate.delete(key);
            return true;
        }
        return false;
    }

    private String generateCode() {
        int code = ThreadLocalRandom.current().nextInt(100000, 999999);
        return String.valueOf(code);
    }
}
