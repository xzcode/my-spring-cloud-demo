package com.mydemo.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * ID 生成器（雪花算法）
 */
public final class IdGenerator {

    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    private IdGenerator() {
    }

    /**
     * 生成雪花ID（字符串）
     */
    public static String nextIdStr() {
        return SNOWFLAKE.nextIdStr();
    }

    /**
     * 生成雪花ID（long）
     */
    public static long nextId() {
        return SNOWFLAKE.nextId();
    }
}
