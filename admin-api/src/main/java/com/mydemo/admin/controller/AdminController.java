package com.mydemo.admin.controller;

import com.mydemo.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "后台管理API")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Operation(summary = "仪表盘数据")
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return adminService.getDashboard();
    }

    @Operation(summary = "用户列表")
    @GetMapping("/users")
    public Map<String, Object> listUsers(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return adminService.listUsers(page, size);
    }

    @Operation(summary = "统计数据")
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return adminService.getStats();
    }
}
