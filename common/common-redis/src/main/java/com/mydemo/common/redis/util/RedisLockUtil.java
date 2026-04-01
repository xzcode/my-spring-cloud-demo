package com.mydemo.common.redis.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis 分布式锁工具
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisLockUtil {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_PREFIX = "lock:";
    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 end";

    /**
     * 尝试加锁
     *
     * @param key     锁的 key
     * @param timeout 锁超时时间
     * @param unit    时间单位
     * @return 锁标识（非 null 表示加锁成功），解锁时需传入
     */
    public String tryLock(String key, long timeout, TimeUnit unit) {
        String lockKey = LOCK_PREFIX + key;
        String lockValue = UUID.randomUUID().toString();
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(lockKey, lockValue, timeout, unit);
        if (Boolean.TRUE.equals(success)) {
            return lockValue;
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param key       锁的 key
     * @param lockValue 加锁时返回的锁标识
     */
    public void unlock(String key, String lockValue) {
        String lockKey = LOCK_PREFIX + key;
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        stringRedisTemplate.execute(script, Collections.singletonList(lockKey), lockValue);
    }
}
