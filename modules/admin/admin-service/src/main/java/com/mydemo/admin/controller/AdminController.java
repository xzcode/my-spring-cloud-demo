package com.mydemo.admin.controller;

import com.mydemo.admin.common.dto.AdminUserDTO;
import com.mydemo.admin.service.AdminService;
import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "管理后台接口")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "创建管理员")
    @PostMapping("/user")
    public Result<AdminUserDTO> create(@RequestBody Map<String, String> body) {
        return Result.ok(adminService.createAdmin(body.get("username"), body.get("realName"), body.get("role")));
    }

    @Operation(summary = "管理员列表")
    @GetMapping("/users")
    public Result<PageResult<AdminUserDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.ok(adminService.listAdmins(page, size));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.ok("admin-service is running");
    }
}
