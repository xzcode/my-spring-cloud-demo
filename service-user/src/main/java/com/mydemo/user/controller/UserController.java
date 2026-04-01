package com.mydemo.user.controller;

import com.mydemo.common.model.Result;
import com.mydemo.common.web.context.UserContext;
import com.mydemo.user.service.AuthService;
import com.mydemo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        return Result.ok(authService.register(request.getUsername(), request.getPassword(), request.getNickname()));
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return Result.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    @Operation(summary = "刷新 Token")
    @PostMapping("/auth/refresh")
    public Result<Map<String, Object>> refreshToken(@RequestBody RefreshRequest request) {
        return Result.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(authHeader);
        return Result.ok();
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public Result<Map<String, Object>> getCurrentUser() {
        String userId = UserContext.getCurrentUserId();
        return Result.ok(userService.getUserById(userId));
    }

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/{userId}")
    public Result<Map<String, Object>> getUserById(@PathVariable String userId) {
        return Result.ok(userService.getUserById(userId));
    }

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
        private String nickname;
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
    }

    @Data
    public static class RefreshRequest {
        @NotBlank(message = "refreshToken不能为空")
        private String refreshToken;
    }
}
