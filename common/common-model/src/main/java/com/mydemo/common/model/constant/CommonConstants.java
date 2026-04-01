package com.mydemo.common.model.constant;

/**
 * 通用常量
 */
public final class CommonConstants {

    private CommonConstants() {
    }

    /** 请求头：用户ID */
    public static final String HEADER_USER_ID = "X-User-Id";
    /** 请求头：用户名 */
    public static final String HEADER_USERNAME = "X-User-Name";
    /** 请求头：用户昵称 */
    public static final String HEADER_NICKNAME = "X-User-Nickname";

    /** Authorization 请求头 */
    public static final String HEADER_AUTHORIZATION = "Authorization";
    /** Bearer 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** Redis key: Token 黑名单 */
    public static final String REDIS_TOKEN_BLACKLIST = "token:blacklist:";
    /** Redis key: Refresh Token */
    public static final String REDIS_REFRESH_TOKEN = "token:refresh:";
}
