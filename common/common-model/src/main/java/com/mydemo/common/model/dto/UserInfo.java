package com.mydemo.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息 DTO（网关传递给下游服务）
 */
@Data
public class UserInfo implements Serializable {

    private String userId;

    private String username;

    private String nickname;
}
