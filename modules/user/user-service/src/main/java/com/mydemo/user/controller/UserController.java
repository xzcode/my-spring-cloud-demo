package com.mydemo.user.controller;

import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import com.mydemo.user.common.dto.UserDTO;
import com.mydemo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<UserDTO> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String nickname = body.get("nickname");
        UserDTO dto = userService.register(username, nickname);
        return Result.ok(dto);
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{userId}")
    public Result<UserDTO> getById(@PathVariable String userId) {
        UserDTO dto = userService.getById(userId);
        return dto != null ? Result.ok(dto) : Result.fail("用户不存在");
    }

    @Operation(summary = "用户列表（分页）")
    @GetMapping("/list")
    public Result<PageResult<UserDTO>> listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(userService.listUsers(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("user-service is running");
    }
}
