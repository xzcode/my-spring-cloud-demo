package com.mydemo.common.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 工具类（Access Token + Refresh Token）
 */
public final class JwtUtil {

    private JwtUtil() {
    }

    /** Access Token 有效期：2小时 */
    public static final long ACCESS_TOKEN_EXPIRE = 2 * 60 * 60 * 1000L;
    /** Refresh Token 有效期：7天 */
    public static final long REFRESH_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    private static final String DEFAULT_SECRET = "MyDemoSpringCloudSecretKey2024ForJWTTokenSign!!";

    private static SecretKey getSignKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        // 确保 key 足够长（至少 256 bits for HS256）
        byte[] paddedKey = new byte[32];
        System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
        return new SecretKeySpec(paddedKey, "HmacSHA256");
    }

    /**
     * 生成 Access Token
     */
    public static String createAccessToken(String userId, String username, String secret) {
        return createToken(userId, username, ACCESS_TOKEN_EXPIRE, secret);
    }

    public static String createAccessToken(String userId, String username) {
        return createAccessToken(userId, username, DEFAULT_SECRET);
    }

    /**
     * 生成 Refresh Token
     */
    public static String createRefreshToken(String userId, String secret) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userId)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(getSignKey(secret))
                .compact();
    }

    public static String createRefreshToken(String userId) {
        return createRefreshToken(userId, DEFAULT_SECRET);
    }

    /**
     * 解析 Token
     */
    public static Claims parseToken(String token, String secret) {
        return Jwts.parser()
                .verifyWith(getSignKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Claims parseToken(String token) {
        return parseToken(token, DEFAULT_SECRET);
    }

    /**
     * 获取 Token 中的 jti
     */
    public static String getJti(String token, String secret) {
        return parseToken(token, secret).getId();
    }

    public static String getJti(String token) {
        return getJti(token, DEFAULT_SECRET);
    }

    /**
     * 获取 Token 中的 userId
     */
    public static String getUserId(String token, String secret) {
        return parseToken(token, secret).getSubject();
    }

    public static String getUserId(String token) {
        return getUserId(token, DEFAULT_SECRET);
    }

    /**
     * 获取 Token 剩余有效时间（毫秒）
     */
    public static long getRemainingExpiration(String token, String secret) {
        Date expiration = parseToken(token, secret).getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }

    public static long getRemainingExpiration(String token) {
        return getRemainingExpiration(token, DEFAULT_SECRET);
    }

    /**
     * 判断是否 Refresh Token
     */
    public static boolean isRefreshToken(String token, String secret) {
        Claims claims = parseToken(token, secret);
        return "refresh".equals(claims.get("type", String.class));
    }

    private static String createToken(String userId, String username, long expireMillis, String secret) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userId)
                .claim("username", username)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireMillis))
                .signWith(getSignKey(secret))
                .compact();
    }
}
