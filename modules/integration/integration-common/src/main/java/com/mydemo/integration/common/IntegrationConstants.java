package com.mydemo.integration.common;

public final class IntegrationConstants {

    private IntegrationConstants() {
    }

    /** 验证码 Redis Key 前缀 */
    public static final String REDIS_VERIFY_CODE_PREFIX = "verify:code:";

    /** 验证码有效时长（秒） */
    public static final int VERIFY_CODE_EXPIRE_SECONDS = 300;

    /** 验证码长度 */
    public static final int VERIFY_CODE_LENGTH = 6;
}
