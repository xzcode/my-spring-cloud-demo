package com.mydemo.user.service;

import com.mydemo.common.model.ErrorCode;
import com.mydemo.common.model.constant.CommonConstants;
import com.mydemo.common.model.exception.BizException;
import com.mydemo.common.redis.util.CacheUtil;
import com.mydemo.common.web.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务：登录、注册、Token 刷新、退出
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final CacheUtil cacheUtil;

    @Value("${app.jwt.secret:MyDemoSpringCloudSecretKey2024ForJWTTokenSign!!}")
    private String jwtSecret;

    /**
     * 注册
     */
    public Map<String, Object> register(String username, String password, String nickname) {
        Map<String, Object> user = userService.createUser(username, password, nickname);
        return generateTokenResponse(String.valueOf(user.get("_id")), username);
    }

    /**
     * 登录
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> user = userService.findByUsername(username);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        if (!password.equals(user.get("password"))) {
            throw new BizException(ErrorCode.PASSWORD_ERROR);
        }
        return generateTokenResponse(String.valueOf(user.get("_id")), username);
    }

    /**
     * 刷新 Token
     */
    public Map<String, Object> refreshToken(String refreshToken) {
        Claims claims;
        try {
            claims = JwtUtil.parseToken(refreshToken, jwtSecret);
        } catch (Exception e) {
            throw new BizException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        if (!"refresh".equals(claims.get("type", String.class))) {
            throw new BizException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        String userId = claims.getSubject();

        // 验证 Redis 中的 Refresh Token 是否一致
        String storedToken = cacheUtil.getStr(CommonConstants.REDIS_REFRESH_TOKEN + userId);
        if (!refreshToken.equals(storedToken)) {
            throw new BizException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        // 获取用户信息
        Map<String, Object> user = userService.getUserById(userId);
        String username = String.valueOf(user.get("username"));

        // 签发新的双 Token（轮换 Refresh Token）
        return generateTokenResponse(userId, username);
    }

    /**
     * 退出登录
     */
    public void logout(String authHeader) {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
            return;
        }
        String token = authHeader.substring(CommonConstants.TOKEN_PREFIX.length());
        try {
            Claims claims = JwtUtil.parseToken(token, jwtSecret);
            String jti = claims.getId();
            String userId = claims.getSubject();

            // 将 Access Token 的 jti 写入黑名单，TTL = 剩余有效时间
            long remainingMillis = JwtUtil.getRemainingExpiration(token, jwtSecret);
            if (remainingMillis > 0) {
                cacheUtil.setStr(CommonConstants.REDIS_TOKEN_BLACKLIST + jti, "1",
                        remainingMillis, TimeUnit.MILLISECONDS);
            }

            // 删除 Refresh Token
            cacheUtil.delete(CommonConstants.REDIS_REFRESH_TOKEN + userId);
        } catch (Exception e) {
            log.warn("退出登录解析 Token 失败", e);
        }
    }

    private Map<String, Object> generateTokenResponse(String userId, String username) {
        String accessToken = JwtUtil.createAccessToken(userId, username, jwtSecret);
        String refreshToken = JwtUtil.createRefreshToken(userId, jwtSecret);

        // Refresh Token 存 Redis
        cacheUtil.setStr(CommonConstants.REDIS_REFRESH_TOKEN + userId, refreshToken,
                JwtUtil.REFRESH_TOKEN_EXPIRE, TimeUnit.MILLISECONDS);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        result.put("expiresIn", JwtUtil.ACCESS_TOKEN_EXPIRE / 1000);
        return result;
    }
}
