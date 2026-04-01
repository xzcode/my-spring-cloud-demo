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
public class EmailService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 发送邮箱验证码（Mock 实现，实际接入邮件服务如 AWS SES）
     */
    public String sendVerifyCode(String email, String bizType) {
        String code = generateCode();
        String key = IntegrationConstants.REDIS_VERIFY_CODE_PREFIX + bizType + ":" + email;

        stringRedisTemplate.opsForValue().set(key, code,
                IntegrationConstants.VERIFY_CODE_EXPIRE_SECONDS, TimeUnit.SECONDS);

        // TODO: 接入真实邮件服务发送
        log.info("[Email Mock] 向 {} 发送验证码: {}，业务类型: {}", email, code, bizType);
        return code;
    }

    /**
     * 验证邮箱验证码
     */
    public boolean verifyCode(String email, String code, String bizType) {
        String key = IntegrationConstants.REDIS_VERIFY_CODE_PREFIX + bizType + ":" + email;
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
