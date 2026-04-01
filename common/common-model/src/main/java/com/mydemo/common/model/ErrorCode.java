package com.mydemo.common.model;

import lombok.Getter;

/**
 * 错误码枚举
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "success"),

    // 通用错误 1xxx
    BAD_REQUEST(1000, "请求参数错误"),
    UNAUTHORIZED(1001, "未登录或 Token 已过期"),
    FORBIDDEN(1003, "无权限访问"),
    NOT_FOUND(1004, "资源不存在"),
    METHOD_NOT_ALLOWED(1005, "请求方法不允许"),
    TOO_MANY_REQUESTS(1029, "请求过于频繁"),

    // 业务错误 2xxx
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_ALREADY_EXISTS(2002, "用户已存在"),
    PASSWORD_ERROR(2003, "密码错误"),
    TOKEN_INVALID(2004, "Token 无效"),
    TOKEN_EXPIRED(2005, "Token 已过期"),
    REFRESH_TOKEN_INVALID(2006, "Refresh Token 无效"),

    // 系统错误 9xxx
    SYSTEM_ERROR(9999, "系统繁忙，请稍后再试"),
    ;

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
