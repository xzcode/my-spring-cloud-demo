package com.mydemo.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydemo.common.model.ErrorCode;
import com.mydemo.common.model.Result;
import com.mydemo.common.model.constant.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * JWT 认证全局过滤器
 * <p>校验 Token → 查 Redis 黑名单 → 解析用户信息 → 写入 Header 传递下游</p>
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Value("${app.jwt.secret:MyDemoSpringCloudSecretKey2024ForJWTTokenSign!!}")
    private String jwtSecret;

    @Value("#{'${app.auth.white-list:/api/user/login,/api/user/register,/api/user/auth/refresh}'.split(',')}")
    private List<String> whiteList;

    public AuthFilter(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 白名单路径直接放行
        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        // 获取 Token
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
            return unauthorizedResponse(exchange, ErrorCode.UNAUTHORIZED);
        }

        String token = authHeader.substring(CommonConstants.TOKEN_PREFIX.length());

        // 解析 Token
        Claims claims;
        try {
            claims = parseToken(token);
        } catch (ExpiredJwtException e) {
            return unauthorizedResponse(exchange, ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            return unauthorizedResponse(exchange, ErrorCode.TOKEN_INVALID);
        }

        String jti = claims.getId();
        String userId = claims.getSubject();
        String username = claims.get("username", String.class);

        // 查 Redis 黑名单
        return redisTemplate.hasKey(CommonConstants.REDIS_TOKEN_BLACKLIST + jti)
                .flatMap(inBlacklist -> {
                    if (Boolean.TRUE.equals(inBlacklist)) {
                        return unauthorizedResponse(exchange, ErrorCode.TOKEN_INVALID);
                    }

                    // 将用户信息写入请求 Header 传递给下游服务
                    ServerHttpRequest mutatedRequest = request.mutate()
                            .header(CommonConstants.HEADER_USER_ID, userId)
                            .header(CommonConstants.HEADER_USERNAME, username != null ? username : "")
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean isWhiteListed(String path) {
        return whiteList.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Claims parseToken(String token) {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        byte[] paddedKey = new byte[32];
        System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
        SecretKey key = new SecretKeySpec(paddedKey, "HmacSHA256");
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ErrorCode errorCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Result<Void> result = Result.fail(errorCode);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(result);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }
    }
}
